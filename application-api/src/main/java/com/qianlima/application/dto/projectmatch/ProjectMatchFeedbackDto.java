package com.qianlima.application.dto.projectmatch;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: hanyuan.yu
 * @create: 2020/4/24 17:41
 * @Version 1.0
 **/
@Data
public class ProjectMatchFeedbackDto implements Serializable {
    private Integer mid;

    private Integer contentId;

    private Integer status;

    private String reason;

    private Integer userId;

}