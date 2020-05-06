package com.qianlima.application.controller.projectmatch;

import com.qianlima.application.dto.projectmatch.ProjectMatchFeedbackDto;
import com.qianlima.application.dto.projectmatch.ProjectMatchRegulationDto;
import com.qianlima.application.dto.projectmatch.ProjectRecordDto;
import com.qianlima.application.request.projectmatch.ProjectMatchFeedbackParam;
import com.qianlima.application.request.projectmatch.ProjectMatchRegulationParam;
import com.qianlima.application.request.projectmatch.ProjectMatchTransfer;
import com.qianlima.application.response.projectmatch.ProjectInfoResp;
import com.qianlima.application.response.projectmatch.ProjectMatchFeedbackResp;
import com.qianlima.application.response.projectmatch.ProjectMatchRegulationResp;
import com.qianlima.application.response.projectmatch.ProjectMatchRegulationRespTransfer;
import com.qianlima.application.service.projectmatch.ProjectMatchService;
import com.qianlima.base.response.ApiResponse;
import com.qianlima.base.response.PageBase;
import com.qianlima.user.api.security.QLMSessionInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 项目匹配
 * @author: hanyuan.yu
 * @create: 2020/4/24 13:58
 * @Version 1.0
 **/
@Api(value = "项目匹配", tags = "官网2.0")
@CrossOrigin("*")
@RestController
@RequestMapping("/project/match")
public class ProjectMatchController {
    @Reference
    private ProjectMatchService projectMatchService;

    @GetMapping("/regulation")
    @ApiOperation(value = "获取项目匹配规则", notes = "code=200 获取列表成功<br>" +
            "300021 您当前没有此功能权限，请升级成VIP会员，详情请咨询客服<br>" +
            "300022 您当前暂未设置项目匹配规则")
    public ApiResponse<ProjectMatchRegulationResp> getProjectMatchRegulation(@ApiIgnore @AuthenticationPrincipal QLMSessionInfo sessionInfo) {
        ProjectMatchRegulationDto projectMatchRegulationDto = projectMatchService.getProjectMatchRegulation(sessionInfo.getId());
        return ApiResponse.success(ProjectMatchRegulationRespTransfer.MAPPER.fromProjectMatchRegulationDto(projectMatchRegulationDto));
    }

    @PostMapping("/regulation")
    @ApiOperation(value = "创建或更新项目匹配规则")
    public ApiResponse<ProjectMatchRegulationResp> createOrUpdateProjectMatchRegulation(@ApiIgnore @AuthenticationPrincipal QLMSessionInfo sessionInfo,
                                                                                        @Valid ProjectMatchRegulationParam projectMatchRegulationParam) {
        ProjectMatchRegulationDto projectMatchRegulationDto = ProjectMatchTransfer.MAPPER.fromProjectMatchRegulationParam(projectMatchRegulationParam);
        projectMatchRegulationDto.setUserId(sessionInfo.getId().intValue());
        projectMatchService.insertOrUpdateProjectMatchRegulation(projectMatchRegulationDto);
        return ApiResponse.success(new ProjectMatchRegulationResp());
    }

    @GetMapping("/record")
    @ApiOperation(value = "获取项目匹配记录", notes = "code=200 获取列表成功<br>" +
            "300021 您当前没有此功能权限，请升级成VIP会员，详情请咨询客服<br>" +
            "300023 您查的项目暂时没有数据")
    public ApiResponse<PageBase<ProjectInfoResp>> getProjectMatchRecordResp(@ApiIgnore @AuthenticationPrincipal QLMSessionInfo sessionInfo,
                                                                            @ApiParam(value = "当前页") @RequestParam Integer pageNo,
                                                                            @ApiParam(value = "当前页数量") @RequestParam Integer pageSize) {
        PageBase<ProjectRecordDto> pageData = projectMatchService.getProjectMatchRecord(sessionInfo.getId().intValue(), pageNo, pageSize);
        List<ProjectInfoResp> list = pageData.getList().stream().map(ProjectMatchRegulationRespTransfer.MAPPER::fromProjectRecordDto).collect(Collectors.toList());
        return ApiResponse.success(new PageBase<>(list, pageData.getTotalSize(), pageData.getPageNo(), pageData.getPageSize()));
    }

    @PostMapping("/feedback")
    @ApiOperation(value = "项目匹配反馈", notes = "code=200 添加反馈成功<br>" +
            "300024 项目不匹配当前用户")
    public ApiResponse<ProjectMatchFeedbackResp> addProjectMatchFeedback(@ApiIgnore @AuthenticationPrincipal QLMSessionInfo sessionInfo,
                                                                         ProjectMatchFeedbackParam projectMatchFeedbackParam) {
        ProjectMatchFeedbackDto projectMatchFeedbackDto = ProjectMatchTransfer.MAPPER.fromProjectMatchFeedbackParam(projectMatchFeedbackParam);
        projectMatchFeedbackDto.setUserId(sessionInfo.getId().intValue());
        projectMatchService.insertProjectMatchFeedback(projectMatchFeedbackDto);
        return ApiResponse.success(new ProjectMatchFeedbackResp());
    }

}