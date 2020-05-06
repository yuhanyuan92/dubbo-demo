package com.qianlima.application.domain.institution;

import lombok.Data;

import java.io.Serializable;

@Data
public class XmCompanyTestF implements Serializable {
    private Integer companyid;
    private String lxr;
    private String phone;
    private String mobile;
}
