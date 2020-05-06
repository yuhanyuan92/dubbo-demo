package com.qianlima.application.controller;

import com.qianlima.application.dto.MyApplicationInfo;
import com.qianlima.application.response.MyApplicationResp;
import com.qianlima.application.service.MyApplicationService;
import com.qianlima.base.response.ApiResponse;
import com.qianlima.user.api.security.QLMSessionInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/myApplicationController")
@Api(value = "我的应用模块接口", tags = "官网2.0")
public class MyApplicationController {

    @Reference
    private MyApplicationService myApplicationService;

    @GetMapping("/getMyApplication")
    @ApiOperation(value = "获取我的应用列表", notes = "官网2.0获取已订购的我的应用列表接口")
    public ApiResponse<List<MyApplicationResp>> getMyApplication(@ApiIgnore @AuthenticationPrincipal QLMSessionInfo sessionInfo){
        List<MyApplicationInfo> myApplicationInfoList = myApplicationService.selectMyApplicationInfo(sessionInfo.getId(), sessionInfo.getUsername());
        List<MyApplicationResp> myApplicationRespList = new ArrayList<>();
        for (MyApplicationInfo myApplicationInfo : myApplicationInfoList) {
            MyApplicationResp myApplicationResp = new MyApplicationResp();
            myApplicationResp.setId(myApplicationInfo.getId());
            myApplicationResp.setAppName(myApplicationInfo.getAppName());
            myApplicationResp.setAppIcon(myApplicationInfo.getAppIcon());
            myApplicationResp.setAppUrl(myApplicationInfo.getAppUrl());
            myApplicationResp.setIsShow(myApplicationInfo.getIsShow());
            myApplicationRespList.add(myApplicationResp);
        }
        return ApiResponse.success(myApplicationRespList);
    }
}
