package com.qianlima.application.dto.publishtender;

import lombok.Data;

import java.io.Serializable;

@Data
public class PublishDto implements Serializable {
    private Integer id;

    private Long userid;

    private String company = "";

    private Integer dwtype = -1;

    private String people = "";

    private String phone = "";

    private String email = "";

    private String title = "";

    private Integer xmtype = -1;

    private String content = "";
}
