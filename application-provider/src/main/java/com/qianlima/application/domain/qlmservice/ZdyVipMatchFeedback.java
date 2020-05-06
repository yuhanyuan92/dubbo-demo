package com.qianlima.application.domain.qlmservice;

public class ZdyVipMatchFeedback {
    private Integer id;

    private Integer mid;

    private Integer cid;

    private Integer status;

    private String reason;

    private Integer inttime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public Integer getInttime() {
        return inttime;
    }

    public void setInttime(Integer inttime) {
        this.inttime = inttime;
    }
}