package com.qianlima.application.dto.companymonitor;

import lombok.Data;

import java.io.Serializable;

@Data
public class CompanyMonitorNameDto implements Serializable {
    /**
     *  企业监控id
     */
    private Integer id;
    /**
     * 企业监控名称
     */
    private String companyName;
    /**
     * 加密的企业监控名称
     */
    private String encryptCompanyName;
}
