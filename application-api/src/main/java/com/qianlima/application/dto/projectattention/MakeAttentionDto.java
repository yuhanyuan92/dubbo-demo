package com.qianlima.application.dto.projectattention;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ZHangYJ
 */
@Data
public class MakeAttentionDto implements Serializable {

    private Integer id;

    private Integer userId;

    private Integer projectId;

    /**
     * 项目标题
     */
    private String projectTitle;

    /**
     * 专耵内容
     */
    private String text;

    private Integer status;

    private Integer addTime;

    private Integer updateTime;
}
