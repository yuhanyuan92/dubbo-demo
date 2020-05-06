package com.qianlima.application.dto.projectmatch;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: hanyuan.yu
 * @create: 2020/4/24 21:23
 * @Version 1.0
 **/
@Data
public class ProjectRecordDto implements Serializable {
    private Integer id;

    private Integer contentId;

    private String title;

    private Integer followCount;

    private String areaName;

    private String businessOwnerType;

    private String followInfo;

    private String investMoney;

    private String updateTime;

    private Integer isUseful;

    private Integer operateFlag;

}