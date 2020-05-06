package com.qianlima.application.dto.database;

import lombok.Data;

import java.io.Serializable;

@Data
public class SjDatabaseDto implements Serializable {
    private String company;
    private String area;
    private String address;
    private String lxr;
    private String fax;
    private String zhiwu;
    private String tel;
    private String phones;

    public void setLxr(String lxr){
        String lxrCopy = "";
        if(lxr != null && lxr.length() > 1){
            lxrCopy += lxr.charAt(0) + "*";
            if(lxr.length()>2){
                lxrCopy += "*";
            }
        }
        this.lxr = lxrCopy;
    }
}
