package com.qianlima.application.domain.qianlima;

import lombok.Data;

import java.io.Serializable;

@Data
public class EnjinfoDetail implements Serializable {
    private Integer gsId;
    private String lxr;
    private String fax;
    private String zhiwu;
    private String phone;
    private String tel;
}
