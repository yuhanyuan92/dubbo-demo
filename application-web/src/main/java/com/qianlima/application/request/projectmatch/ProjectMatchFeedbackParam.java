package com.qianlima.application.request.projectmatch;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 项目匹配反馈
 * @author: hanyuan.yu
 * @create: 2020/4/24 16:46
 * @Version 1.0
 **/
@ApiModel(value = "项目匹配反馈请求")
@Data
public class ProjectMatchFeedbackParam {

    @ApiModelProperty(value = "zdy_vip_match主键")
    private Integer mid;

    @ApiModelProperty(value = "项目id")
    private Integer contentId;

    @ApiModelProperty(value = "1有价值 0无价值")
    private Integer status;

    @ApiModelProperty(value = "原因")
    private String reason;
}