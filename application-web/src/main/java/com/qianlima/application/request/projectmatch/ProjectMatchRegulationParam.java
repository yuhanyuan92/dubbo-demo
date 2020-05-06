package com.qianlima.application.request.projectmatch;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author hanyuan.yu
 */
@ApiModel(value = "项目匹配规则提交请求")
@Data
public class ProjectMatchRegulationParam {
    @ApiModelProperty(value = "主键")
    private Integer id;

    @NotEmpty(message = "业务范围不能为空")
    @ApiModelProperty(value = "业务范围")
    private String businessName;

    @NotEmpty(message = "地区不能为空")
    @ApiModelProperty(value = "选中地区id")
    private String areaIds;
}