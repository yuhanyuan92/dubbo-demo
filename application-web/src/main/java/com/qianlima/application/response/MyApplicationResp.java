package com.qianlima.application.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "我的应用信息", description = "我的应用信息返回值")
public class MyApplicationResp implements Serializable {
    @ApiModelProperty(value = "我的应用id")
    private Integer id;
    @ApiModelProperty(value = "我的应用icon")
    private String appIcon;
    @ApiModelProperty(value = "我的应用跳转连接")
    private String appUrl;
    @ApiModelProperty(value = "我的应用名称")
    private String appName;

    @ApiModelProperty(value = "是否是已订购的应用： 0否  1是")
    private Integer isShow;
}
