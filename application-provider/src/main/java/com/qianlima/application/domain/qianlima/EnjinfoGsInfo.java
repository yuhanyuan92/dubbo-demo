package com.qianlima.application.domain.qianlima;

import lombok.Data;

import java.io.Serializable;

@Data
public class EnjinfoGsInfo implements Serializable {
    private Integer id;
    private Integer areaid;
    private String address;
    private String gsmc;
}
