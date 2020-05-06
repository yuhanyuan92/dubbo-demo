package com.qianlima.application.dto.projectmatch;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: hanyuan.yu
 * @create: 2020/4/24 14:23
 * @Version 1.0
 **/
@Data
public class ProjectMatchRegulationDto implements Serializable {
    private Integer id;

    private Integer userId;

    private String businessName;

    private String areaIds;

    private String areaNames;
}