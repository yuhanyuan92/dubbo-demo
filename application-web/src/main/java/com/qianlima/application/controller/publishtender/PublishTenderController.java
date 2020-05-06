package com.qianlima.application.controller.publishtender;

import com.qianlima.account.service.redis.RedisService;
import com.qianlima.application.dto.publishtender.PublishDto;
import com.qianlima.application.request.publishtender.PublishParam;
import com.qianlima.application.service.publishtender.PublishTenderService;
import com.qianlima.base.response.ApiResponse;
import com.qianlima.user.api.security.QLMSessionInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Slf4j
@Api(value = "发布招标", tags = "发布招标")
@RestController
@RequestMapping("/publish/tender")
@CrossOrigin("*")
public class PublishTenderController {

    @Reference
    private PublishTenderService publishTenderService;
    @Reference
    private RedisService redisService;

    public final static String RANDOMCODEKEY="publish:tender:info:";


    @GetMapping(value = "/getPublished")
    @ApiOperation(value = "获取列表", notes = "获取列表接口:" +
            "10001  未登录用户")
    public ApiResponse getPublished(
            Integer pageNo,Integer pageSize,
            @ApiIgnore @AuthenticationPrincipal QLMSessionInfo qlmSessionInfo){
        if (qlmSessionInfo == null){
            return ApiResponse.error(10001,"未登录用户");
        }
        if (pageNo == null){
            pageNo = 0;
        }
        if (pageSize == null){
            pageNo = 30;
        }
        return ApiResponse.success(publishTenderService.getDataByUserid(qlmSessionInfo.getId(),pageNo,pageSize));
    }

    @GetMapping(value = "/getPublishedByid/{id}")
    @ApiOperation(value = "修改回显", notes = "修改回显接口:" +
            "10001  未登录用户")
    public ApiResponse getPublishedByid(
            @PathVariable Integer id,
            @ApiIgnore @AuthenticationPrincipal QLMSessionInfo qlmSessionInfo){
        if (qlmSessionInfo == null){
            return ApiResponse.error(10001,"未登录用户");
        }
        return ApiResponse.success(publishTenderService.getDataById(id));
    }

    @PostMapping(value = "/topublish")
    @ApiOperation(value = "发布", notes = "发布招标接口:" +
            "10001  未登录用户" +
            "10002  验证码错误")
    public ApiResponse topublish(
            @Valid PublishParam publishParam,
            @ApiIgnore HttpSession session,
            @ApiIgnore @AuthenticationPrincipal QLMSessionInfo qlmSessionInfo){
        if (qlmSessionInfo == null){
            return ApiResponse.error(10001,"未登录用户");
        }
        String random = redisService.getRedis(RANDOMCODEKEY+qlmSessionInfo.getId());
        log.info("验证码 ：-----{}",random);
        if (random == null || !publishParam.getYzm().equals(random)) {
            log.info("发布招标接口  --  验证码校验失败");
            return ApiResponse.error(10002,"验证码错误");
        }
        session.removeAttribute(RANDOMCODEKEY);
        PublishDto publishDto = new PublishDto();
        BeanUtils.copyProperties(publishParam,publishDto);
        publishDto.setUserid(qlmSessionInfo.getId());
        return ApiResponse.success(publishTenderService.insertPublishTener(publishDto));
    }

    @PostMapping(value = "/uppublished")
    @ApiOperation(value = "更新", notes = "更新发布招标接口:" +
            "10001  未登录用户" +
            "10002  更新失败")
    public ApiResponse uppublished(
            @Valid PublishParam publishParam,
            @ApiIgnore @AuthenticationPrincipal QLMSessionInfo qlmSessionInfo){
        if (qlmSessionInfo == null){
            return ApiResponse.error(10001,"未登录用户");
        }
        PublishDto publishDto = new PublishDto();
        BeanUtils.copyProperties(publishParam,publishDto);
        publishDto.setUserid(qlmSessionInfo.getId());
        int result = publishTenderService.updatePublishTender(publishDto);
        if (result > 0){
            return ApiResponse.success("更新成功");
        }else {
            return ApiResponse.error(10002,"更新失败");
        }
    }

    @GetMapping("{id}/cancel")
    @ApiOperation(value = "删除", notes = "删除发布招标接口:" +
            "10001  未登录用户" +
            "10002  删除失败")
    public ApiResponse cancel(
            @PathVariable Integer id,
            @ApiIgnore @AuthenticationPrincipal QLMSessionInfo qlmSessionInfo){
        if (qlmSessionInfo == null){
            return ApiResponse.error(10001,"未登录用户");
        }
        int result = publishTenderService.deletePublishTender(id);
        if (result > 0){
            return ApiResponse.success("删除成功");
        }else {
            return ApiResponse.error(10002,"删除失败");
        }
    }

    @GetMapping(value = "/getVerify")
    @ApiOperation(value = "生成验证码", notes = "生成验证码接口")
    public void getVerify(@ApiIgnore @AuthenticationPrincipal QLMSessionInfo qlmSessionInfo,
                          @ApiIgnore HttpServletResponse response) {
        try {
            //设置相应类型,告诉浏览器输出的内容为图片
            response.setContentType("image/jpeg");
            //设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
            //输出验证码图片方法
//            session.setAttribute(RANDOMCODEKEY, randomValidateCode.getRandcode(response));
            redisService.setRedis(RANDOMCODEKEY+qlmSessionInfo.getId(),randomValidateCode.getRandcode(response));
        } catch (Exception e) {
            log.error("获取验证码失败>>>>   ", e);
        }
    }
}
