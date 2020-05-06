package com.qianlima.application.dto.projectattention;

import lombok.Data;

import java.io.Serializable;

@Data
public class DatabaseParamDto implements Serializable {
    private Long userid;

    private String username;

    private Integer areaid = 2703;

    private String company = "";

    private Integer pageNo = 1;

    private Integer pageSize = 30;

    private String zhiwu = "";
}
