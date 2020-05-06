package com.qianlima.application.service.impl.projectmatch;

import com.github.pagehelper.Page;
import com.qianlima.application.domain.qianlima.ZdyXsInfo;
import com.qianlima.application.domain.qlmservice.ZdyVipMatchCids;
import com.qianlima.application.domain.qlmservice.ZdyVipMatchFeedback;
import com.qianlima.application.domain.qlmservice.ZdyVipMatchWithBLOBs;
import com.qianlima.application.dto.projectmatch.ProjectMatchFeedbackDto;
import com.qianlima.application.dto.projectmatch.ProjectMatchRegulationDto;
import com.qianlima.application.dto.projectmatch.ProjectRecordDto;
import com.qianlima.application.exception.ApplicationExceptionCode;
import com.qianlima.application.exception.MyApplicationException;
import com.qianlima.application.manage.transfer.ProjectMatchDtoTransfer;
import com.qianlima.application.mapper.qianlima.ZdyXsInfoMapper;
import com.qianlima.application.mapper.qlmservice.ZdyVipMatchCidsMapper;
import com.qianlima.application.mapper.qlmservice.ZdyVipMatchFeedbackMapper;
import com.qianlima.application.mapper.qlmservice.ZdyVipMatchMapper;
import com.qianlima.application.service.projectmatch.ProjectMatchService;
import com.qianlima.application.utils.AesKitUtils;
import com.qianlima.application.utils.HttpRequestUtil;
import com.qianlima.base.response.PageBase;
import com.qianlima.service.area.api.AreaService;
import com.qianlima.service.content.api.ProjectService;
import com.qianlima.service.content.dto.ProjectDetailDto;
import com.qianlima.service.domain.ZdyVipTrace;
import com.qianlima.user.api.dto.ZdyMembersDto;
import com.qianlima.user.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: 项目匹配
 * @author: hanyuan.yu
 * @create: 2020/4/24 14:32
 * @Version 1.0
 **/
@Slf4j
@Service(retries = 0, timeout = 5000)
public class ProjectMatchServiceImpl implements ProjectMatchService {
    @Value("${findCrmMemberInfo.url}")
    private String crmMemberInfoUrl;
    @Reference
    private UserService userService;
    @Reference
    private AreaService areaService;
    @Reference
    private ProjectService projectService;
    @Autowired
    private ZdyVipMatchMapper zdyVipMatchMapper;
    @Autowired
    private ZdyXsInfoMapper zdyXsInfoMapper;
    @Autowired
    private ZdyVipMatchFeedbackMapper zdyVipMatchFeedbackMapper;
    @Autowired
    private ZdyVipMatchCidsMapper zdyVipMatchCidsMapper;

    @Override
    public ProjectMatchRegulationDto getProjectMatchRegulation(Long userId) {
        ZdyMembersDto zdyMembersDto = userService.getAllZdyMembersById(userId);
        int level = zdyMembersDto.getDengji();
        if (level < 60) {
            throw new MyApplicationException(ApplicationExceptionCode.PROJECT_MATCH_NO_AUTH_ERROR);
        }
        ZdyVipMatchWithBLOBs zdyVipMatchWithBLOBs = zdyVipMatchMapper.selectByUserId(userId);
        if (zdyVipMatchWithBLOBs == null) {
            throw new MyApplicationException(ApplicationExceptionCode.PROJECT_MATCH_REGULATION_NOT_FOUND_ERROR);
        }
        String areas = zdyVipMatchWithBLOBs.getAreas();
        if (StringUtils.isBlank(areas)) {
            zdyVipMatchWithBLOBs.setAreas("2703");
        }
        return ProjectMatchDtoTransfer.MAPPER.fromZdyVipMatchWithBLOBs(zdyVipMatchWithBLOBs);
    }

    @Override
    public void insertOrUpdateProjectMatchRegulation(ProjectMatchRegulationDto projectMatchRegulationDto) {
        int userId = projectMatchRegulationDto.getUserId();
        long count = zdyVipMatchMapper.countByUserId(userId);
        String areaIds = projectMatchRegulationDto.getAreaIds();
        String areaNames = getAreaNames(areaIds);
        String areas = areaIds.contains("2703") ? "" : areaIds;
        String businessName = projectMatchRegulationDto.getBusinessName();
        int time = (int) (System.currentTimeMillis() / 1000);
        ZdyVipMatchWithBLOBs zdyVipMatchWithBLOBs = new ZdyVipMatchWithBLOBs();
        zdyVipMatchWithBLOBs.setAreas(areas);
        zdyVipMatchWithBLOBs.setAreanames(areaNames);
        zdyVipMatchWithBLOBs.setUserid(userId);
        zdyVipMatchWithBLOBs.setYewu(businessName);
        zdyVipMatchWithBLOBs.setUpdatetime(time);
        if (count > 0) {
            zdyVipMatchMapper.updateByUserIdSelective(zdyVipMatchWithBLOBs);
        } else {
            String companyName = "";
            int startTime = 0;
            int endTime = 0;
            String url = String.format(crmMemberInfoUrl, userId);
            try {
                String s = HttpRequestUtil.sendHttpGet(url);
                JSONObject jsonObject = JSONObject.fromObject(s);
                if (jsonObject != null) {
                    if (jsonObject.get("dwmc") != null && !jsonObject.get("dwmc").equals("null")) {
                        companyName = (String) jsonObject.get("dwmc");
                    }
                    if (jsonObject.get("sttime") != null && !(jsonObject.get("sttime") + "").equals("null")) {
                        startTime = Integer.valueOf(jsonObject.get("sttime") + "");
                    }
                    if (jsonObject.get("totime") != null && !(jsonObject.get("totime") + "").equals("null")) {
                        endTime = Integer.valueOf(jsonObject.get("totime") + "");
                    }
                }
            } catch (IOException e) {
                log.info("请求crm接口错误！");
                e.printStackTrace();
            }
            ZdyXsInfo zdyXsInfo = zdyXsInfoMapper.getCustomerServiceInfo(userId);
            String customerName = "";
            int customerId = 0;
            if (zdyXsInfo != null) {
                customerName = zdyXsInfo.getKfname();
                customerId = zdyXsInfo.getId();
            }
            zdyVipMatchWithBLOBs.setDwmc(companyName);
            zdyVipMatchWithBLOBs.setKfid(customerId);
            zdyVipMatchWithBLOBs.setKfname(customerName);
            zdyVipMatchWithBLOBs.setIsornot(1);
            zdyVipMatchWithBLOBs.setIndustrytype(2);
            zdyVipMatchWithBLOBs.setIntime(time);
            zdyVipMatchWithBLOBs.setStarttime(startTime);
            zdyVipMatchWithBLOBs.setEndtime(endTime);
            zdyVipMatchMapper.insertSelective(zdyVipMatchWithBLOBs);
        }
    }

    @Override
    public void insertProjectMatchFeedback(ProjectMatchFeedbackDto projectMatchFeedbackDto) {
        int userId = projectMatchFeedbackDto.getUserId();
        ZdyVipMatchWithBLOBs zdyVipMatchWithBLOBs = zdyVipMatchMapper.selectByUserId(Long.valueOf(userId));
        int matchId = zdyVipMatchWithBLOBs.getId();
        int mid = projectMatchFeedbackDto.getMid();
        if (matchId != mid) {
            throw new MyApplicationException(ApplicationExceptionCode.PROJECT_NOT_MATCH_FEEDBACK_ERROR);
        }
        ZdyVipMatchFeedback zdyVipMatchFeedback = ProjectMatchDtoTransfer.MAPPER.fromProjectMatchFeedbackDto(projectMatchFeedbackDto);
        zdyVipMatchFeedback.setInttime((int) (System.currentTimeMillis() / 1000));
        zdyVipMatchFeedbackMapper.insertSelective(zdyVipMatchFeedback);
    }

    @Override
    public PageBase<ProjectRecordDto> getProjectMatchRecord(Integer userId, Integer pageNo, Integer pageSize) {
        ProjectMatchRegulationDto projectMatchRegulationDto = getProjectMatchRegulation(Long.valueOf(userId));
        int matchId = projectMatchRegulationDto.getId();
        Page<ZdyVipMatchCids> pageData = zdyVipMatchCidsMapper.selectByMatchId(matchId, pageNo, pageSize);
        if (pageData == null || pageData.getTotal() == 0) {
            throw new MyApplicationException(ApplicationExceptionCode.PROJECT_MATCH_RECORD_NOT_FOUND_ERROR);
        }
        List<ZdyVipMatchCids> list = pageData.getResult();
        List<Integer> contentIds = list.stream().map(e -> e.getCid()).collect(Collectors.toList());
        List<ProjectDetailDto> projectDetailDtoList = projectService.getProjectSampleDetailById(contentIds);
        List<ProjectRecordDto> recordList = list.stream().map(ProjectMatchDtoTransfer.MAPPER::fromZdyVipMatchCids).collect(Collectors.toList());
        recordList = recordList.stream().peek(projectRecordDto -> {
            ProjectDetailDto dto = projectDetailDtoList.stream()
                    .filter(projectDetailDto -> projectDetailDto.getContentId().equals(projectRecordDto.getContentId().toString()))
                    .findAny().orElse(new ProjectDetailDto());
            projectRecordDto.setBusinessOwnerType(StringUtils.isNotBlank(dto.getProjectType()) ? dto.getProjectType() : "暂未确定");

            List<ZdyVipTrace> tracesList = dto.getZdyVipTracesList();
            int followCount = 0;
            String followINfo = "";
            StringBuffer buffer = new StringBuffer();
            if (null != tracesList && !tracesList.isEmpty()) {
                ZdyVipTrace zdyVipTrace = tracesList.get(0);
                followCount = dto.getZdyVipTracesList().size();
                buffer.append("跟进").append(followCount).append(":").append(zdyVipTrace.getJzjd())
                        .append(",").append(zdyVipTrace.getI_suggestion());
                followINfo = buffer.toString();
            }
            projectRecordDto.setFollowCount(followCount);
            projectRecordDto.setFollowInfo(followINfo);
            projectRecordDto.setAreaName(dto.getAreaName());
            projectRecordDto.setInvestMoney(dto.getInvestment());
            projectRecordDto.setTitle(dto.getTitle());
            projectRecordDto.setUpdateTime(dto.getUpdateTime());
            getOperateFlag(matchId, projectRecordDto);
        }).collect(Collectors.toList());
        return new PageBase<>(recordList, pageData.getTotal(), pageData.getPageNum(), pageData.getPageSize());
    }

    /**
     * 获取地区名称
     *
     * @param areaIds
     * @return
     */
    private String getAreaNames(String areaIds) {
        String areaNames;
        if (areaIds.contains("2703")) {
            areaNames = "全国";
        } else {
            String[] idList = areaIds.split(",");
            List<Map<String, Object>> areaList = areaService.selectByIds(Arrays.asList(idList));
            areaNames = areaList.stream().map(e -> e.get("name").toString()).collect(Collectors.joining(","));
        }
        return areaNames;
    }

    /**
     * 判断反馈按钮是否可用
     *
     * @param matchId
     * @param projectRecordDto
     * @return
     */
    private ProjectRecordDto getOperateFlag(Integer matchId, ProjectRecordDto projectRecordDto) {
        ZdyVipMatchFeedback zdyVipMatchFeedback = zdyVipMatchFeedbackMapper.selectByContentId(matchId, projectRecordDto.getContentId());
        int operateFlag = 0;
        if (zdyVipMatchFeedback != null) {
            if (zdyVipMatchFeedback.getStatus().intValue() == 2) {
                operateFlag = 1;
            } else {
                projectRecordDto.setIsUseful(zdyVipMatchFeedback.getStatus());
            }
        }
        projectRecordDto.setOperateFlag(operateFlag);
        return projectRecordDto;
    }

}