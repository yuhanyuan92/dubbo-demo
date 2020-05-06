package com.qianlima.application.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "测试入参", description = "测试入参对象")
public class TestParams implements Serializable {
    @ApiModelProperty(value = "账户id")
    private Integer id;
    @ApiModelProperty(value = "账户名称")
    private String name;
}
