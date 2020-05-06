package com.qianlima.application.domain.qianlima;

import lombok.Data;

import java.io.Serializable;

@Data
public class ZdyMembersXwgz implements Serializable {
    private Long userid;
    private String username;
    private String fwsj;
    private Integer weight;
    private Integer sourcetype;
    private String regtime;
}
