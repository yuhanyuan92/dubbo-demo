package com.qianlima.application.service.impl.database;

import com.qianlima.application.domain.institution.XmCompanyTest;
import com.qianlima.application.domain.institution.XmCompanyTestF;
import com.qianlima.application.domain.qianlima.EnjinfoDetail;
import com.qianlima.application.domain.qianlima.EnjinfoGsInfo;
import com.qianlima.application.domain.qianlima.ZdyMembersXwgz;
import com.qianlima.application.domain.qianlima.ZdySjkShow;
import com.qianlima.application.dto.database.QyDatabaseDto;
import com.qianlima.application.dto.projectattention.DatabaseParamDto;
import com.qianlima.application.dto.database.SjDatabaseDto;
import com.qianlima.application.mapper.institution.XmCompanyTestFMapper;
import com.qianlima.application.mapper.institution.XmCompanyTestMapper;
import com.qianlima.application.mapper.qianlima.EnjinfoMapper;
import com.qianlima.application.mapper.qianlima.ZdyMembersXwgzMapper;
import com.qianlima.application.mapper.qianlima.ZdySjkShowMapper;
import com.qianlima.application.mapper.qlmlog.SearchQyLogMapper;
import com.qianlima.application.repo.SolrRepository;
import com.qianlima.application.service.database.DatabaseService;
import com.qianlima.application.utils.AesKitUtils;
import com.qianlima.user.api.dto.ZdyMembersDto;
import com.qianlima.user.api.service.UserService;
import com.qianlima.service.area.api.AreaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * 企业库、设计库
 */
@Service
@Slf4j
public class DatabaseServiceImpl implements DatabaseService {

    @Reference
    private UserService userService;
    @Reference
    private AreaService areaService;

    @Autowired
    private ZdySjkShowMapper zdySjkShowMapper;
    @Autowired
    private SolrRepository solrRepository;
    @Autowired
    private XmCompanyTestMapper xmCompanyTestMapper;
    @Autowired
    private XmCompanyTestFMapper xmCompanyTestFMapper;
    @Autowired
    private SearchQyLogMapper searchQyLogMapper;
    @Autowired
    private ZdyMembersXwgzMapper zdyMembersXwgzMapper;
    @Autowired
    private EnjinfoMapper enjinfoMapper;

    @Override
    public boolean getSupplierPermission(Long userid) {
        ZdyMembersDto zdyMembersDto = userService.getAllZdyMembersById(userid);
        log.info("供应商权限 - 用户信息 -{}",zdyMembersDto.toString());
        if (zdyMembersDto.getDengji() > 30) {
            return true;
        }
        return false;
    }

    @Override
    public boolean getPermission(String username, int type) {
        ZdySjkShow zdySjkShow = zdySjkShowMapper.selectZdySjkShowInfo(username);
        if (zdySjkShow != null) {
            // 到期时间
            Long validtime = zdySjkShow.getValidtime();
            int state = 0;
            if (type == 0) {
                // 开通状态 -- 企业库
                state = zdySjkShow.getQysjk();
            } else if (type == 1) {
                // 开通状态 -- 设计库
                state = zdySjkShow.getSjsjk();
            }
            if (state == 1 && validtime >= Instant.now().getEpochSecond()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean getBrowsePermission(String username) {
        Integer count = searchQyLogMapper.selectCountByUsername(username, getTodayTimestamps());
        if (count < 40) {
            return true;
        }
        return false;
    }

    @Override
    public Map<String, Object> getQyDatabase(DatabaseParamDto databaseParamDto) {
        Map<String, Object> params = new HashMap<String, Object>(4) {{
            this.put("numPerPage", databaseParamDto.getPageSize());
            this.put("currentPage", databaseParamDto.getPageNo());
            this.put("gsmc", databaseParamDto.getCompany());
            this.put("areaid", databaseParamDto.getAreaid());
        }};
        List<Integer> idsFromQySolr = solrRepository.getIdsFromQySolr(params);
        log.info("qysolr - {}",idsFromQySolr.toString());
        int total = idsFromQySolr.get(0);
        idsFromQySolr.remove(0);
        if (idsFromQySolr.size() == 0) {
            log.info("qysolr - null return ");
            return new HashMap<String, Object>(1) {{
                this.put("data", Collections.EMPTY_LIST);
            }};
        }
        List<XmCompanyTest> xmCompanyTests = xmCompanyTestMapper.selectDataByIds(idsFromQySolr);
        List<Integer> companyids = xmCompanyTests.stream().map(XmCompanyTest::getId).collect(Collectors.toList());
        if (companyids.size() == 0) {
            log.info("companyids - null return ");
            return new HashMap<String, Object>(1) {{
                this.put("data", Collections.EMPTY_LIST);
            }};
        }
        List<XmCompanyTestF> xmCompanyTestFS = xmCompanyTestFMapper.selectDataByIds(companyids);
        List<QyDatabaseDto> databaseDtos = new ArrayList<QyDatabaseDto>() {{
            xmCompanyTests.forEach(xmCompanyTest -> {
                xmCompanyTest.setArea(areaService.getNameByAreaId(xmCompanyTest.getAreaid()));
                QyDatabaseDto databaseDto = new QyDatabaseDto();
                BeanUtils.copyProperties(xmCompanyTest, databaseDto);
                List<QyDatabaseDto.PeopleInfo> list = new ArrayList<>();
                xmCompanyTestFS.forEach(xmCompanyTestF -> {
                    if (xmCompanyTest.getId().equals(xmCompanyTestF.getCompanyid())) {
                        QyDatabaseDto.PeopleInfo peopleInfo = new QyDatabaseDto.PeopleInfo();
                        peopleInfo.setLxr(xmCompanyTestF.getLxr());
                        String phones = getPhone(xmCompanyTestF.getPhone(), xmCompanyTestF.getMobile());
                        peopleInfo.setPhones(AesPhone(showString(phones)));
                        list.add(peopleInfo);
                    }
                });
                databaseDto.setPeoples(list);
                this.add(databaseDto);
            });
        }};
        //增加用户行为跟踪
        add_xwgz(databaseParamDto.getUserid(), 8);
        if (total > 0) {
            addSearchQyLog(databaseParamDto.getUsername(), getUrl(databaseParamDto),"");
        }
        return new HashMap<String, Object>(2) {{
            this.put("total", total);
            this.put("data", databaseDtos);
            this.put("pageSize", databaseParamDto.getPageSize());
        }};
    }

    @Override
    public Map<String, Object> getSjDatabase(DatabaseParamDto databaseParamDto) {
        Map<String, Object> params = new HashMap<String, Object>(4) {{
            this.put("numPerPage", databaseParamDto.getPageSize());
            this.put("currentPage", databaseParamDto.getPageNo());
            this.put("dwmc", databaseParamDto.getCompany());
            this.put("zhiwu", databaseParamDto.getZhiwu());
            this.put("areaid", databaseParamDto.getAreaid());
        }};
        List<Integer> idsFromSjSolr = solrRepository.getIdsFromSjSolr(params);
        log.info("sjsolr - {}",idsFromSjSolr.toString());
        int total = idsFromSjSolr.get(0);
        idsFromSjSolr.remove(0);
        if (idsFromSjSolr.size() == 0) {
            log.info("sjsolr - null return ");
            return new HashMap<String, Object>(1) {{
                this.put("data", Collections.EMPTY_LIST);
            }};
        }
        List<EnjinfoDetail> enjinfoDetails = enjinfoMapper.selectEnjinfoDetailByIds(idsFromSjSolr);
        List<Integer> gsids = enjinfoDetails.stream().map(EnjinfoDetail::getGsId).collect(Collectors.toList());
        List<EnjinfoGsInfo> enjinfoGsInfos = enjinfoMapper.selectEnjinfoGsInfoByIds(gsids);
        List<SjDatabaseDto> sjDatabaseDtos = new ArrayList<SjDatabaseDto>() {{
            enjinfoDetails.forEach(enjinfoDetail -> {
                SjDatabaseDto sjDatabaseDto = new SjDatabaseDto();
                BeanUtils.copyProperties(enjinfoDetail, sjDatabaseDto);
                enjinfoGsInfos.forEach(enjinfoGsInfo -> {
                    if (enjinfoDetail.getGsId().equals(enjinfoGsInfo.getId())) {
                        sjDatabaseDto.setArea(areaService.getNameByAreaId(enjinfoGsInfo.getAreaid()));
                        sjDatabaseDto.setAddress(enjinfoGsInfo.getAddress());
                        sjDatabaseDto.setCompany(enjinfoGsInfo.getGsmc());
                    }
                });
                sjDatabaseDto.setPhones(AesPhone(showString(enjinfoDetail.getPhone())));
                this.add(sjDatabaseDto);
            });
        }};
        //增加用户行为跟踪
        add_xwgz(databaseParamDto.getUserid(), 8);
        return new HashMap<String, Object>(2) {{
            this.put("total", total);
            this.put("data", sjDatabaseDtos);
            this.put("pageSize", databaseParamDto.getPageSize());
        }};
    }

    private Long getTodayTimestamps() {
        Long currentTimestamps = System.currentTimeMillis();
        Long oneDayTimestamps = Long.valueOf(60 * 60 * 24 * 1000);
        return currentTimestamps - (currentTimestamps + 60 * 60 * 8 * 1000) % oneDayTimestamps;
    }

    private String getPhone(String phone, String mobile) {
        if (StringUtils.isNotBlank(phone) && StringUtils.isNotBlank(mobile)) {
            return showString(phone) + "," + showString(mobile);
        } else if (StringUtils.isNotBlank(phone) && StringUtils.isEmpty(mobile)) {
            return showString(phone);
        } else if (StringUtils.isNotBlank(mobile) && StringUtils.isEmpty(phone)) {
            return showString(mobile);
        } else {
            return "";
        }
    }

    private String showString(String s) {
        if (s == null || "null".equals(s)) {
            return "";
        }
        return s;
    }

    private static String AesPhone(String phones) {
        //只匹配出一个手机号加密,座机号可无限制匹配
        String regex = "^[1]([3-9])[0-9]{9}$";
        Pattern numPattern = Pattern.compile(regex);
        String aesMobile = "";
        boolean matcherMobile = false;
        for (String tmpStr : phones.split(",")) {
            tmpStr = tmpStr.trim();
            int tmpLen = tmpStr.length();
            if (tmpLen > 0) {
                if (tmpLen > 10) {
                    String tmpStr2 = tmpStr.substring(tmpLen - 11);
                    if (numPattern.matcher(tmpStr2).matches()) {
                        if (matcherMobile) {
                            continue;
                        }
                        tmpStr = AesKitUtils.encrypt(tmpStr2);
                        matcherMobile = true;
                    }
                }
                aesMobile += tmpStr + ",";
            }
        }
        int tmpLen = aesMobile.length();
        if (tmpLen > 0) {
            aesMobile = aesMobile.substring(0, tmpLen - 1);
        }
        return aesMobile;
    }

    public void add_xwgz(Long userid, int weight) {
        //获取用户信息
        ZdyMembersDto zdyMembersDto = userService.getAllZdyMembersById(userid);
        ZdyMembersXwgz zdyMembersXwgz = new ZdyMembersXwgz();
        zdyMembersXwgz.setUserid(userid);
        zdyMembersXwgz.setUsername(zdyMembersDto.getUsername());
        zdyMembersXwgz.setRegtime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(zdyMembersDto.getRegTime()));
        zdyMembersXwgz.setWeight(weight);
        zdyMembersXwgz.setSourcetype(getSource(userid, zdyMembersDto.getUsername(), zdyMembersDto.getIp()));
        zdyMembersXwgz.setFwsj(getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));

        zdyMembersXwgzMapper.insertUser(zdyMembersXwgz);
    }

    //根据userid、username、ip 获取用户来源
    public int getSource (Long userid, String username, String ip){
        log.info("获取用户来源的参数：用户ID= {} ;用户名：{}; IP: {}",userid,username,ip);
        //result 0：网站 1:百度2:360 3:搜狗4:移动SEM 5:移动App
        if (ip != null && ip.equals("")) {
            if (ip.equals("111.111.111.111")) {
                return 5;
            } else if (ip.equals("222.222.222.222")) {
                int m_from = zdyMembersXwgzMapper.selectFromByUserid(userid);
                if (m_from == 7) {
                    return 4;
                }
            }
        }
        Integer unit = zdyMembersXwgzMapper.selectUnittypeByUsername(username);
        if (unit > 99 && unit < 200) {
            return 1;
        } else if (unit > 199 && unit < 300) {
            return 2;
        } else if (unit > 299 && unit < 400) {
            return 3;
        }
        return 0;
    }
    private void addSearchQyLog(String username,String requestUrl,String ip){
        int searchResultCount = searchQyLogMapper.selectCountByUrl(username,requestUrl,getTodayTimestamps());
        if (searchResultCount == 0) {
            //如果没有，则新增，有则不记录
            searchQyLogMapper.insertJL(username, requestUrl, ip, getTodayTimestamps());
        }
    }

    private String getUrl(DatabaseParamDto databaseParamDto){
        String url = "http://center.qianlima.com:80/db_qy.jsp?pg="+databaseParamDto.getPageNo();
        if (databaseParamDto.getAreaid()!=null){
            url+="&p_area="+databaseParamDto.getAreaid();
        }
        if (databaseParamDto.getCompany()!=null){
            url+="&gsmc="+databaseParamDto.getCompany();
        }
        return url;
    }

    private static String getCurrentDateTime(String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.now().format(formatter);
    }

}
