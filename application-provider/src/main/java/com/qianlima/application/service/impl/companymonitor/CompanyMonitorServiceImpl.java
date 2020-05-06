package com.qianlima.application.service.impl.companymonitor;

import com.qianlima.application.domain.qianlima.ZdyDingyueAll;
import com.qianlima.application.dto.companymonitor.CompanyMonitorNameDto;
import com.qianlima.application.mapper.qianlima.ZdyDingyueAllMapper;
import com.qianlima.application.mapper.qlmservice.XsJiankongMapper;
import com.qianlima.application.dto.companymonitor.ZdyDingyueAllDto;
import com.qianlima.application.service.companymonitor.CompanyMonitorService;
import com.qianlima.application.utils.AesKitUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CompanyMonitorServiceImpl implements CompanyMonitorService {

    @Autowired
    private XsJiankongMapper xsJiankongMapper;
    @Autowired
    private ZdyDingyueAllMapper zdyDingyueAllMapper;

    @Override
    public List<CompanyMonitorNameDto> getCompanyNameList(Long id) {
        List<ZdyDingyueAll> zdyDingyueAlls = zdyDingyueAllMapper.selectListById(id);
        List<CompanyMonitorNameDto> companyMonitorNameDtos = new ArrayList<CompanyMonitorNameDto>(){{
            zdyDingyueAlls.forEach(zdyDingyueAll -> {
                CompanyMonitorNameDto companyMonitorNameDto = new CompanyMonitorNameDto();
                companyMonitorNameDto.setId(zdyDingyueAll.getId());
                companyMonitorNameDto.setCompanyName(zdyDingyueAll.getTitle());
                companyMonitorNameDto.setEncryptCompanyName(AesKitUtils.encrypt(zdyDingyueAll.getTitle()));
                this.add(companyMonitorNameDto);
            });
        }};
        return companyMonitorNameDtos;
    }
    @Override
    public Integer modifyCompanyInfo(ZdyDingyueAllDto zdyDingyueAllDto) {
        ZdyDingyueAll zdyDingyueAll = new ZdyDingyueAll();
        BeanUtils.copyProperties(zdyDingyueAllDto,zdyDingyueAll);
        return zdyDingyueAllMapper.updateByPrimaryKeySelective(zdyDingyueAll);
    }

    @Override
    public Integer delCompanyInfo(Integer id) {
        return zdyDingyueAllMapper.deleteByPrimaryKey(id);
    }

    @Override
    public CompanyMonitorNameDto getCompanyInfo(Integer id) {
        CompanyMonitorNameDto companyMonitorNameDto = new CompanyMonitorNameDto();
      ZdyDingyueAll zdyDingyueAll =  zdyDingyueAllMapper.selectByPrimaryKey(id);
      if(zdyDingyueAll !=null){
          companyMonitorNameDto.setCompanyName(zdyDingyueAll.getTitle());
          companyMonitorNameDto.setId(id);
      }
        return companyMonitorNameDto;
    }

    @Override
    public Boolean companyMonitorPermission(Long userid) {
        int result = xsJiankongMapper.selectCountByUserid(userid,System.currentTimeMillis()/1000);
        if (result>0){
            return true;
        }
        return false;
    }

    @Override
    public Integer addCompanyMonitor(String titles, Long userid) {
        String[] title = titles.split(",");
        int total = getCountMonitorByUserid(userid);
        int size = title.length;
        if ((total+size)>6){
            //超过 6 条
            log.info("新增企业监控失败! 现有 {} 条, 新增 {} 条" ,total,size);
            return 10002;
        }
        for (String t : title){
            int result = zdyDingyueAllMapper.selectCountByTitleAndUserid(userid,t);
            if (result>0){
                log.info("新增企业监控失败! 用户 - {}, 监控 - {}，已存在！" ,userid,t);
                return 10003;
            }
            ZdyDingyueAll zdyDingyueAll = new ZdyDingyueAll();
            zdyDingyueAll.setTitle(t);
            zdyDingyueAll.setUserid(userid.intValue());
            zdyDingyueAll.setIprogs("0 = 0,0 = 1,0 = 2,0 = 3");
            zdyDingyueAll.setIareas("");
            zdyDingyueAll.setIntime(Integer.parseInt(System.currentTimeMillis()/1000+""));
            zdyDingyueAll.setUptime(Integer.parseInt(System.currentTimeMillis()/1000+""));
            zdyDingyueAll.setType(1);
            zdyDingyueAllMapper.insertSelective(zdyDingyueAll);
        }
        return 200;
    }

    private Integer getCountMonitorByUserid(Long userid){
        return zdyDingyueAllMapper.selectCountByUserid(userid);
    }


}
