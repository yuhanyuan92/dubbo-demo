package com.qianlima.application.domain.qlmservice;

public class ZdyVipMatch {
    private Integer id;

    private Integer userid;

    private String dwmc;

    private String areas;

    private Integer kfid;

    private String kfname;

    private Integer kelevel;

    private Integer isornot;

    private Integer industrytype;

    private Integer status;

    private String notice;

    private Integer intime;

    private Integer updatetime;

    private Integer starttime;

    private Integer endtime;

    private Integer ismatch;

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

    public String getDwmc() {
        return dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc == null ? null : dwmc.trim();
    }

    public String getAreas() {
        return areas;
    }

    public void setAreas(String areas) {
        this.areas = areas == null ? null : areas.trim();
    }

    public Integer getKfid() {
        return kfid;
    }

    public void setKfid(Integer kfid) {
        this.kfid = kfid;
    }

    public String getKfname() {
        return kfname;
    }

    public void setKfname(String kfname) {
        this.kfname = kfname == null ? null : kfname.trim();
    }

    public Integer getKelevel() {
        return kelevel;
    }

    public void setKelevel(Integer kelevel) {
        this.kelevel = kelevel;
    }

    public Integer getIsornot() {
        return isornot;
    }

    public void setIsornot(Integer isornot) {
        this.isornot = isornot;
    }

    public Integer getIndustrytype() {
        return industrytype;
    }

    public void setIndustrytype(Integer industrytype) {
        this.industrytype = industrytype;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice == null ? null : notice.trim();
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

    public Integer getStarttime() {
        return starttime;
    }

    public void setStarttime(Integer starttime) {
        this.starttime = starttime;
    }

    public Integer getEndtime() {
        return endtime;
    }

    public void setEndtime(Integer endtime) {
        this.endtime = endtime;
    }

    public Integer getIsmatch() {
        return ismatch;
    }

    public void setIsmatch(Integer ismatch) {
        this.ismatch = ismatch;
    }
}