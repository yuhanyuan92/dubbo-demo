package com.qianlima.application.domain.institution;

import lombok.Data;

import java.io.Serializable;

@Data
public class XmCompanyTest implements Serializable {
    private Integer id;
    private String company;
    private Integer areaid;
    private String address;
    private String area;
}
