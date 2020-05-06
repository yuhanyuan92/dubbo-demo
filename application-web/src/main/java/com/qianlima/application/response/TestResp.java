package com.qianlima.application.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "测试返回参数", description = "测试返回参数对象")
public class TestResp implements Serializable {
    @ApiModelProperty(value = "账户id")
    private Integer id;
    @ApiModelProperty(value = "账户名称")
    private String name;
}
