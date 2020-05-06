package com.qianlima.application.domain.qlmservice;

public class ZdyCustomerToxm {
    private Integer id;

    private Integer userid;

    private String username;

    private Integer validatetime;

    private Integer totalcount;

    private Integer inttime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Integer getValidatetime() {
        return validatetime;
    }

    public void setValidatetime(Integer validatetime) {
        this.validatetime = validatetime;
    }

    public Integer getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(Integer totalcount) {
        this.totalcount = totalcount;
    }

    public Integer getInttime() {
        return inttime;
    }

    public void setInttime(Integer inttime) {
        this.inttime = inttime;
    }
}