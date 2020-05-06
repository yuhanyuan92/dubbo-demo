package com.qianlima.application.response.projectmatch;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 项目列表信息
 * @author: hanyuan.yu
 * @create: 2020/4/24 16:02
 * @Version 1.0
 **/
@Data
@ApiModel(value = "项目匹配记录列表数据")
public class ProjectInfoResp {
    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "contentId")
    private Integer contentId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "跟进数量")
    private Integer followCount;

    @ApiModelProperty(value = "地区名称")
    private String areaName;

    @ApiModelProperty(value = "业主类型")
    private String businessOwnerType;

    @ApiModelProperty(value = "跟进信息")
    private String followInfo;

    @ApiModelProperty(value = "投资金额")
    private String investMoney;

    @ApiModelProperty(value = "更新时间")
    private String updateTime;

    @ApiModelProperty(value = "0无价值 1有价值")
    private Integer isUseful;

    @ApiModelProperty(value = "价值反馈按钮是否可操作 0不可操作 1可操作")
    private Integer operateFlag;
}