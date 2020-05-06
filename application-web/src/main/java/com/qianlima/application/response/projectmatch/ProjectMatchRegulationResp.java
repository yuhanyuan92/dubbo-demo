package com.qianlima.application.response.projectmatch;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 项目匹配
 * @author: hanyuan.yu
 * @create: 2020/4/24 13:49
 * @Version 1.0
 **/
@Data
@ApiModel(value = "项目匹配规则")
public class ProjectMatchRegulationResp {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "业务范围")
    private String businessName;

    @ApiModelProperty(value = "选中地区id")
    private String areaIds;

    @ApiModelProperty(value = "地区")
    private String areaNames;

}