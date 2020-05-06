package com.qianlima.application.dto.publishtender;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class AuditUserZbxxDto implements Serializable {
    private Integer id;
    private String title;
    private Integer xmtype;
    private String time;
    private Integer status;
    private String contentUrl;

    public void setTime(Long timeStamp){
        this.time = timeStampToString(timeStamp);
    }
    private static String timeStampToString(Long timeStamp){
        if (timeStamp.toString().length()>10){
            timeStamp = Long.valueOf(timeStamp.toString().substring(0, 10));
        }
        Instant instant = Instant.ofEpochMilli(timeStamp * 1000L);
        ZoneId zone = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = instant.atZone(zone);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return zonedDateTime.format(dateTimeFormatter);
    }
}
