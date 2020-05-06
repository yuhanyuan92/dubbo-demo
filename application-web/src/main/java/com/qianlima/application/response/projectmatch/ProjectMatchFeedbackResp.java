package com.qianlima.application.response.projectmatch;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 项目匹配反馈
 * @author: hanyuan.yu
 * @create: 2020/4/24 16:38
 * @Version 1.0
 **/
@Data
@ApiModel(value = "项目匹配反馈")
public class ProjectMatchFeedbackResp {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "zdy_vip_match主键")
    private Integer mid;

    @ApiModelProperty(value = "项目id")
    private Integer contentId;

    @ApiModelProperty(value = "1有价值 0无价值 2全部")
    private Integer status;

    @ApiModelProperty(value = "原因")
    private String reason;

}