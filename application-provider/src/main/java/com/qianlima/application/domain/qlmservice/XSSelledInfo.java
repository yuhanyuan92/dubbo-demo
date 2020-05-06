package com.qianlima.application.domain.qlmservice;

public class XSSelledInfo {
    private Integer id;

    private Integer cid;

    private Integer xsid;

    private Integer sttime;

    private Integer totime;

    private Integer level;

    private Integer price;

    private String beizhu;

    private Integer isxf;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getXsid() {
        return xsid;
    }

    public void setXsid(Integer xsid) {
        this.xsid = xsid;
    }

    public Integer getSttime() {
        return sttime;
    }

    public void setSttime(Integer sttime) {
        this.sttime = sttime;
    }

    public Integer getTotime() {
        return totime;
    }

    public void setTotime(Integer totime) {
        this.totime = totime;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu == null ? null : beizhu.trim();
    }

    public Integer getIsxf() {
        return isxf;
    }

    public void setIsxf(Integer isxf) {
        this.isxf = isxf;
    }
}