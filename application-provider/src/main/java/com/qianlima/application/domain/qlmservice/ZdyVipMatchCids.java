package com.qianlima.application.domain.qlmservice;

public class ZdyVipMatchCids {
    private Integer id;

    private Integer mid;

    private Integer pid;

    private Integer cid;

    private String xmjd;

    private Integer status;

    private Integer intime;

    private Integer updatetime;

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

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getXmjd() {
        return xmjd;
    }

    public void setXmjd(String xmjd) {
        this.xmjd = xmjd == null ? null : xmjd.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIntime() {
        return intime;
    }

    public void setIntime(Integer intime) {
        this.intime = intime;
    }

    public Integer getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Integer updatetime) {
        this.updatetime = updatetime;
    }
}