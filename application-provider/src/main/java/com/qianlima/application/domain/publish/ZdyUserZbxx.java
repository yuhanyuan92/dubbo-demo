package com.qianlima.application.domain.publish;

import lombok.Data;

import java.io.Serializable;

@Data
public class ZdyUserZbxx implements Serializable {
    private Integer id;
    private Long userid;
    private String company;
    private String people;
    private String phone;
    private String email;
    private String title;
    private String content;
    private Integer xmtype;
    private Long addtime;
}
