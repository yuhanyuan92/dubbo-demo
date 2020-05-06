package com.qianlima.application.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MyApplicationInfo implements Serializable {
    private Integer id;
    private String appIcon;
    private String appUrl;
    private String appName;

    private Integer isShow;
}
