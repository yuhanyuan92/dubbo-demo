package com.qianlima.application.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Aspect
@Configuration
@Slf4j
public class MethodParamsAspect {
    /**
     * 切入点表达式
     * 监控com.test.service..*.*(..))包及其子包的所有public方法
     */
    @Pointcut("execution(* com.qianlima.application.service..*.*(..))")
    private void pointCutMethod() {
    }

    /**
     * 前置通知
     * @throws Throwable
     */
    @Before("pointCutMethod()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        log.info("请求CLASS_METHOD :{} " ,joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("请求参数值 : {}" , Arrays.toString(joinPoint.getArgs()));
    }
}
