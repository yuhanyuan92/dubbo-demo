package com.qianlima.application.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Configuration
@Slf4j
public class WebMvcParamsAspect {
    /**
     * 切入点表达式
     * 监控com.test.controller..*.*(..))包及其子包的所有public方法
     */
    @Pointcut("execution(* com.qianlima.application.controller..*.*(..))")
    private void pointCutMethod() {
    }

    /**
     * 前置通知
     * @throws Throwable
     */
    @Before("pointCutMethod()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        /**
         * RequestContextHolder:SpringMVC 提供了一个非常方便的工具类,能够在任意地方很方便的获取 request 和 session 对象。
         * 先通过工具类RequestContextHolder得到ServletRequestAttributes,然后再得到我们的请求对象.HttpServletRequest.
         */
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(null != attributes){
            HttpServletRequest request = attributes.getRequest();
            // 记录下请求内容
            log.info("请求URL : {}" , request.getRequestURL().toString());
            log.info("请求IP地址 : {}" , request.getRemoteAddr());
            log.info("请求HTTP_METHOD : {}" , request.getMethod());
            log.info("请求CLASS_METHOD :{} " ,joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            log.info("请求参数值 : {}" , Arrays.toString(joinPoint.getArgs()));
        }
    }

}
