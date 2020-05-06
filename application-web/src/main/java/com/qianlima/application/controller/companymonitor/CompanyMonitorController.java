package com.qianlima.application.controller.companymonitor;

import com.qianlima.application.dto.companymonitor.CompanyMonitorNameDto;
import com.qianlima.application.dto.companymonitor.ZdyDingyueAllDto;
import com.qianlima.application.response.companymonitor.CompanyMonitorNameResp;
import com.qianlima.application.response.companymonitor.CompanyMonitorNameRespTransfer;
import com.qianlima.application.service.companymonitor.CompanyMonitorService;
import com.qianlima.base.response.ApiResponse;
import com.qianlima.user.api.security.QLMSessionInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Api(value = "企业监控", tags = "官网2.0")
@RestController
@RequestMapping("/company/monitor")
@CrossOrigin("*")
public class CompanyMonitorController {

    @Reference
    private CompanyMonitorService companyMonitorService;

    @GetMapping("/list")
    @ApiOperation(value = "获取已配置的监控企业名称", notes = "用户回显展示")
    public ApiResponse<List<CompanyMonitorNameResp>> getCompanyNameList(@ApiIgnore @AuthenticationPrincipal QLMSessionInfo sessionInfo){
        List<CompanyMonitorNameDto> dtoList = companyMonitorService.getCompanyNameList(sessionInfo.getId());
        List<CompanyMonitorNameResp> respList = new ArrayList<>();
        for (CompanyMonitorNameDto dto : dtoList) {
            respList.add(CompanyMonitorNameRespTransfer.MAPPER.fromCompanyMonitorNameDto(dto));
        }
        return ApiResponse.success(respList);
    }

    @GetMapping("/modifyCompanyInfo")
    @ApiOperation(value = "修改企业信息", notes = "修改企业信息 1 修改成功 0 处理失败")
    public ApiResponse<Integer> modifyCompanyInfo(ZdyDingyueAllDto zdyDingyueAll, @ApiIgnore @AuthenticationPrincipal QLMSessionInfo sessionInfo){

        Integer flag =0;
        if(sessionInfo.getId()>0){
             flag =  companyMonitorService.modifyCompanyInfo(zdyDingyueAll);
        }
        return ApiResponse.success(flag);
    }

    @GetMapping("/delCompanyInfo")
    @ApiOperation(value = "删除企业信息", notes = "删除企业信息 1 删除成功 0 处理失败")
    public ApiResponse<Integer> delCompanyInfo(Integer id, @ApiIgnore @AuthenticationPrincipal QLMSessionInfo sessionInfo){

        Integer flag =0;
        if(sessionInfo.getId()>0){
            flag =  companyMonitorService.delCompanyInfo(id);
        }
        return ApiResponse.success(flag);
    }

    @GetMapping("/getCompanyInfo")
    @ApiOperation(value = "获取企业信息", notes = "根据主键获取企业信息")
    public ApiResponse<CompanyMonitorNameResp> getCompanyInfo(Integer id, @ApiIgnore @AuthenticationPrincipal QLMSessionInfo sessionInfo){
        CompanyMonitorNameResp companyMonitorNameResp = new CompanyMonitorNameResp();
        if(sessionInfo.getId()>0){

            CompanyMonitorNameDto companyMonitorNameDto = companyMonitorService.getCompanyInfo(id);
            companyMonitorNameResp.setCompanyName(companyMonitorNameDto.getCompanyName());
            companyMonitorNameResp.setId(id);
        }
        return ApiResponse.success(companyMonitorNameResp);
    }


    @PostMapping("/addAll")
    @ApiOperation(value = "未设置监控页面-新增企业监控", notes = "未设置监控页面-新增企业监控" +
            "titles - 多个 , 隔开" +
            "10001 未登录" +
            "10002 条数上线" +
            "200 成功")
    public ApiResponse addCompanyMonitor(
            String titles,
            @ApiIgnore @AuthenticationPrincipal QLMSessionInfo sessionInfo){
        if (sessionInfo == null){
            return ApiResponse.error(10001,"未登录");
        }
        int code = companyMonitorService.addCompanyMonitor(titles,sessionInfo.getId());
        if (code == 10002){
            return ApiResponse.error(code,"新增企业监控失败，条数上限");
        }else if (code == 10003){
            return ApiResponse.error(code,"新增企业监控失败，重复监控");
        }
        return ApiResponse.success("新增企业监控成功！");
    }

    @PostMapping("/permission")
    @ApiOperation(value = "未设置监控页面-新增企业监控", notes = "未设置监控页面-新增企业监控")
    public ApiResponse monitorPermission(
            @ApiIgnore @AuthenticationPrincipal QLMSessionInfo sessionInfo){
        if (sessionInfo == null){
            return ApiResponse.error(10001,"未登录");
        }
        return ApiResponse.success(companyMonitorService.companyMonitorPermission(sessionInfo.getId()));
    }
}
