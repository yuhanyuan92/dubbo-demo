package com.qianlima.application.controller.database;

import com.qianlima.application.dto.projectattention.DatabaseParamDto;
import com.qianlima.application.request.database.QyDatabaseParam;
import com.qianlima.application.request.database.SjDatabaseParam;
import com.qianlima.application.service.database.DatabaseService;
import com.qianlima.user.api.security.QLMSessionInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import com.qianlima.base.response.ApiResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;


@Slf4j
@Api(value = "企业库、设计库", tags = "企业库、设计库")
@RestController
@RequestMapping("/database")
@CrossOrigin("*")
public class DatabaseController {

    @Reference
    private DatabaseService databaseService;

    private final List<String> allowUserAndIp = Arrays.asList("13874864885","qianlima444");

    @GetMapping(value = "/supplierPermission")
    @ApiOperation(value = "供应商认证权限", notes = "供应商认证权限接口")
    public ApiResponse getPermission(
            @ApiIgnore @AuthenticationPrincipal QLMSessionInfo qlmSessionInfo){
        return ApiResponse.success(databaseService.getSupplierPermission(qlmSessionInfo.getId()));
    }

    @GetMapping(value = "/qyDatabase")
    @ApiOperation(value = "企业数据库", notes = "企业数据库接口:" +
            "10001  未登录用户" +
            "10002  用户无权限" +
            "10003  浏览上限")
    public ApiResponse getQyCompanyDatabase(
            QyDatabaseParam qyDatabaseParam,
            @ApiIgnore @AuthenticationPrincipal QLMSessionInfo qlmSessionInfo){
        if (qlmSessionInfo == null){
            return ApiResponse.error(10001,"未登录用户");
        }
        boolean permission = databaseService.getPermission(qlmSessionInfo.getUsername(),0);
        if (allowUserAndIp.contains(qlmSessionInfo.getUsername())){
            permission = true;
        }
        if (!permission){
            return ApiResponse.error(10002,"无权限");
        }
        permission = databaseService.getBrowsePermission(qlmSessionInfo.getUsername());
        if (!permission){
            return ApiResponse.error(10003,"浏览上限");
        }
        DatabaseParamDto databaseParamDto = new DatabaseParamDto();
        BeanUtils.copyProperties(qyDatabaseParam,databaseParamDto);
        databaseParamDto.setUserid(qlmSessionInfo.getId());
        databaseParamDto.setUsername(qlmSessionInfo.getUsername());
        return ApiResponse.success(databaseService.getQyDatabase(databaseParamDto));
    }

    @GetMapping(value = "/sjDatabase")
    @ApiOperation(value = "设计单位数据库", notes = "设计单位数据库接口:" +
            "10001  未登录用户" +
            "10002  用户无权限")
    public ApiResponse getSjCompanyDatabase(
            SjDatabaseParam sjDatabaseParam,
            @ApiIgnore @AuthenticationPrincipal QLMSessionInfo qlmSessionInfo){
        if (qlmSessionInfo == null){
            return ApiResponse.error(10001,"未登录用户");
        }
        boolean permission = databaseService.getPermission(qlmSessionInfo.getUsername(),1);
        if (allowUserAndIp.contains(qlmSessionInfo.getUsername())){
            permission = true;
        }
        if (!permission){
            return ApiResponse.error(10002,"无权限");
        }
        DatabaseParamDto databaseParamDto = new DatabaseParamDto();
        BeanUtils.copyProperties(sjDatabaseParam,databaseParamDto);
        databaseParamDto.setUserid(qlmSessionInfo.getId());
        databaseParamDto.setUsername(qlmSessionInfo.getUsername());
        return ApiResponse.success(databaseService.getSjDatabase(databaseParamDto));
    }

}
