package com.qianlima.application.config.exception.handler;

import com.qianlima.application.config.exception.exception.CompanyServiceExpiredException;
import com.qianlima.application.config.exception.exception.ExceedLimitException;
import com.qianlima.application.config.exception.exception.ExceptionCode;
import com.qianlima.application.config.exception.exception.ParameterException;
import com.qianlima.base.exception.ServiceException;
import com.qianlima.base.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理服务提供者异常
     * @param e {@link ServiceException}
     */
    @ExceptionHandler(value= ServiceException.class)
    public ApiResponse<String> exceptionHandler(ServiceException e){
        return ApiResponse.error(e);
    }

    /**
     * 处理请求字段验证
     * @param e 验证异常
     * @return 校验结果
     */
    @ExceptionHandler(value= BindException.class)
    public ApiResponse<String> exceptionHandler(BindException e){
        String errorMessage = e.getBindingResult().getFieldErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));
        return ApiResponse.error(401, errorMessage);
    }

    @ExceptionHandler(value=Exception.class)
    public ApiResponse exceptionHandler(Exception e){
        e.printStackTrace();
        if (e instanceof CompanyServiceExpiredException){
            // 自定义异常类
            log.info("企业服务到期：{}", e.getMessage());
            return ApiResponse.error(ExceptionCode.PARAM_INVALID,e.getMessage());
        }else if (e instanceof ParameterException){
            // 自定义异常类
            log.info("请求参数错误：{}", e.getMessage());
            return ApiResponse.error(ExceptionCode.PARAM_INVALID,e.getMessage());
        }else if (e instanceof ExceedLimitException){
            // 自定义异常类
            log.info(ExceptionCode.PARAM_INVALID+"");
            return ApiResponse.error(ExceptionCode.PARAM_INVALID,e.getMessage());
        }
        return ApiResponse.error(e.getMessage());
    }
}
