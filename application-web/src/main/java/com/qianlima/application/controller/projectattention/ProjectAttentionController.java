package com.qianlima.application.controller.projectattention;

import com.qianlima.application.dto.projectattention.MakeAttentionDto;
import com.qianlima.application.request.projectattention.CancelAttentionParam;
import com.qianlima.application.request.projectattention.MakeAttentionParam;
import com.qianlima.application.request.projectattention.MakeAttentionTransfer;
import com.qianlima.application.response.projectattention.ProjectAttentionResp;
import com.qianlima.application.response.projectattention.ProjectAttentionRespTransfer;
import com.qianlima.application.service.projectattention.ProjectAttentionService;
import com.qianlima.base.response.ApiResponse;
import com.qianlima.base.response.PageBase;
import com.qianlima.service.area.api.AreaService;
import com.qianlima.service.content.api.ProjectService;
import com.qianlima.service.content.dto.ProjectDetailDto;
import com.qianlima.user.api.security.QLMSessionInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 项目专盯
 * @author ZHangYJ
 */
@Slf4j
@Api(value = "项目专耵", tags = "官网2.0")
@CrossOrigin("*")
@RestController
@RequestMapping("/project/attention")
public class ProjectAttentionController {

    @Reference
    private ProjectAttentionService projectAttentionService;
    @Reference
    private ProjectService projectService;
    @Reference
    private AreaService areaService;

    @PostMapping
    @ApiOperation(value = "项目专盯", notes = "code=200 专耵成功<br>" +
            "300010 您没有申请权限！请升级为高级会员或高级以上级别会员。<br>" +
            "300011 您的专盯条数已用尽，请联系您的专属客服！<br>" +
            "300012 该项目不存在或已删除。" +
            "300013 项目专耵失败，请重试" +
            "300014 该项目已被您取消专盯，请联系您的专属客服。")
    public ApiResponse<ProjectAttentionResp> makeAttention(
            @Valid MakeAttentionParam makeAttentionParam,
            @ApiIgnore @AuthenticationPrincipal QLMSessionInfo sessionInfo){

        MakeAttentionDto dto = MakeAttentionTransfer.MAPPER.toMakeAttentionDto(makeAttentionParam);
        dto.setUserId(sessionInfo.getId().intValue());
        projectAttentionService.makeAttention(dto);
        log.info("makeAttentionParam : {}", makeAttentionParam);
        return ApiResponse.success(new ProjectAttentionResp());
    }

    @PostMapping("{id}/cancel")
    @ApiOperation(value = "取消项目专盯", notes = "code=200 取消专耵成功<br>" +
            "300020 取消专耵失败，请刷新页面重试")
    public ApiResponse<ProjectAttentionResp> cancelAttention(@PathVariable Integer id,
                                                             @ApiIgnore @AuthenticationPrincipal QLMSessionInfo sessionInfo){

        MakeAttentionDto dto = projectAttentionService.cancel(sessionInfo.getId().intValue(), id);
        return ApiResponse.success(ProjectAttentionRespTransfer.MAPPER.fromMakeAttention(dto));
    }

    @PostMapping("cancel")
    @ApiOperation(value = "批量取消项目专盯", notes = "code=200 专耵成功<br>" +
            "300020 取消专耵失败，请刷新页面重试")
    public ApiResponse<List<ProjectAttentionResp>> cancelAttention(@Valid CancelAttentionParam cancelAttentionParam,
                                                             @ApiIgnore @AuthenticationPrincipal QLMSessionInfo sessionInfo){

        List<ProjectAttentionResp> respList = new ArrayList<>(cancelAttentionParam.getIdArr().size());
        for (Integer id : cancelAttentionParam.getIdArr()){
            MakeAttentionDto dto = projectAttentionService.cancel(sessionInfo.getId().intValue(), id);
            respList.add(ProjectAttentionRespTransfer.MAPPER.fromMakeAttention(dto));
        }
        return ApiResponse.success(respList);
    }

    @GetMapping
    @ApiOperation(value = "项目专盯列表")
    public ApiResponse<PageBase<ProjectAttentionResp>> attentions(@ApiIgnore @AuthenticationPrincipal QLMSessionInfo sessionInfo,
                                                                  @ApiParam PageBase pageBase){

        PageBase<MakeAttentionDto> attentionDtoPage = projectAttentionService.getAttentionByUserId(sessionInfo.getId().intValue(), pageBase.getPageNo(), pageBase.getPageSize());
        List<Integer> contentIdList = attentionDtoPage.getList().stream().map(MakeAttentionDto::getProjectId).collect(Collectors.toList());
        List<ProjectDetailDto> projectDetailDtoList = projectService.getProjectSampleDetailById(contentIdList);
        List<ProjectAttentionResp> projectAttentionResps = attentionDtoPage.getList().stream()
                .map(ProjectAttentionRespTransfer.MAPPER::fromMakeAttention)
                .peek(projectAttentionResp -> {
                    ProjectDetailDto dto = projectDetailDtoList.stream()
                            .filter(projectDetailDto -> projectDetailDto.getContentId().equals(projectAttentionResp.getProjectId().toString()))
                            .findAny().orElse(new ProjectDetailDto());
                    projectAttentionResp.setInvestment(dto.getInvestment());
                    projectAttentionResp.setOwnerType(dto.getProjectType());
                    projectAttentionResp.setProgress(dto.getProjectStage());
                    projectAttentionResp.setDecoration(dto.getDecoration());
                    projectAttentionResp.setProg(dto.getProjectCategory());
                    projectAttentionResp.setAreaName(dto.getProvince());
                    projectAttentionResp.setTrace(dto.getZdyVipTracesList() == null ? 0 : dto.getZdyVipTracesList().size());
                    projectAttentionResp.setCatId(dto.getCatId());
                    projectAttentionResp.setAreaName(dto.getAreaName());
                }).collect(Collectors.toList());
        return ApiResponse.success(new PageBase<>(projectAttentionResps, attentionDtoPage.getTotalSize(), attentionDtoPage.getPageNo(), attentionDtoPage.getPageSize()));
    }

    @ApiOperation(value = "获取用户对该项目的专耵状态")
    @GetMapping("byprojectid")
    public ApiResponse<ProjectAttentionResp> attention(@ApiIgnore @AuthenticationPrincipal QLMSessionInfo sessionInfo,
                                                       @ApiParam("项目id") @RequestParam Integer projectId){
        MakeAttentionDto makeAttentionDto = projectAttentionService.getAttention(sessionInfo.getId().intValue(), projectId);
        return ApiResponse.success(ProjectAttentionRespTransfer.MAPPER.fromMakeAttention(makeAttentionDto));
    }
}
