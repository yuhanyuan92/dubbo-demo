package com.qianlima.application.dto.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
public class QyDatabaseDto implements Serializable {
    private String company;
    private String area;
    private String address;
    private List<PeopleInfo> peoples;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PeopleInfo implements Serializable{
        private String lxr;
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

}
