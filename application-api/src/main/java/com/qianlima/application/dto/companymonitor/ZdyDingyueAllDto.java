package com.qianlima.application.dto.companymonitor;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: TODO
 * @Author: Mr.Song
 * @Date: 2020/4/24 22:56
 * @Version
 */
@Data
public class ZdyDingyueAllDto implements Serializable {

    private Integer id;

    private String title;

    private String iprogs;

    private String iareas;

    private Integer intime;

    private Integer uptime;

    private Integer userid;

    private Integer type;
}
