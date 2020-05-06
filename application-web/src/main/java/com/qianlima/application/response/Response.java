package com.qianlima.application.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
@ApiModel(value = "通用返回对象", description = "通用返回对象")
public class Response<T> {

    @ApiModelProperty(value = "状态码")
    private int code;

    @ApiModelProperty(value = "状态码对应的提示信息")
    private String msg;

    @ApiModelProperty(value = "返回实体数据")
    private T data;

    public static <T> Response<T> success(T data){
        return new Response<>(HttpStatus.OK.value(), "", data);
    }

    public static <T> Response<T> error(T data){
        return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "", data);
    }

    /*public static <T> Response<T> error(ErrorCodeEnum errorCodeEnum){
        return new Response<>(errorCodeEnum.getErrorCode(), errorCodeEnum.getErrorMsg(), null);
    }*/

    public static <T> Response<T> error(int code, String msg){
        return new Response<>(code, msg, null);
    }
}