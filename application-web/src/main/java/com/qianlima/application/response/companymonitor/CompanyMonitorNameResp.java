package com.qianlima.application.response.companymonitor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "企业监控名称", description = "企业监控名称，用于回显")
public class CompanyMonitorNameResp implements Serializable {

    @ApiModelProperty(value = "企业监控id")
    private Integer id;

    @ApiModelProperty(value = "企业监控名称")
    private String companyName;

    @ApiModelProperty(value = "加密的企业监控名称")
    private String encryptCompanyName;
}
