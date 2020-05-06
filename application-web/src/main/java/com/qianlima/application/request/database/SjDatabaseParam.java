package com.qianlima.application.request.database;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "设计数据库")
@Data
public class SjDatabaseParam implements Serializable {

    @ApiModelProperty(value = "地区id")
    private Integer areaid = 2703;

    @ApiModelProperty(value = "公司名称")
    private String company = "";

    @ApiModelProperty(value = "职务")
    private String zhiwu = "";

    @ApiModelProperty(value = "当前页")
    private Integer pageNo = 1;

    @ApiModelProperty(value = "每页条数")
    private Integer pageSize = 30;
}
