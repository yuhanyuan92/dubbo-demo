package com.qianlima.application.request.publishtender;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "设计数据库")
@Data
public class PublishParam implements Serializable {
    @ApiModelProperty(value = "发布id")
    private Integer id = -1;

    @NotEmpty(message = "发布单位不能为空")
    @ApiModelProperty(value = "发布单位")
    private String company;

    @NotNull(message = "单位类型不能为空")
    @ApiModelProperty(value = "单位类型")
    private Integer dwtype;

    @NotEmpty(message = "发布人不能为空")
    @ApiModelProperty(value = "发布人")
    private String people;

    @NotEmpty(message = "电话不能为空")
    @ApiModelProperty(value = "电话")
    private String phone;

    @NotEmpty(message = "邮箱不能为空")
    @ApiModelProperty(value = "邮箱")
    private String email;

    @NotEmpty(message = "标题不能为空")
    @ApiModelProperty(value = "标题")
    private String title;

    @NotNull(message = "项目类型不能为空")
    @ApiModelProperty(value = "项目类型")
    private Integer xmtype;

    @NotEmpty(message = "内容不能为空")
    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "验证码")
    private String yzm;
}
