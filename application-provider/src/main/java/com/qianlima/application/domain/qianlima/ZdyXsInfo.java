package com.qianlima.application.domain.qianlima;

import java.util.Date;

public class ZdyXsInfo {
    private Integer id;

    private Integer crmid;

    private String kfname;

    private String kfphone;

    private String kffixphone;

    private String kfmail;

    private String kfqq;

    private String wechatcode;

    private Date gmtCreate;

    private Date gmtUpdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCrmid() {
        return crmid;
    }

    public void setCrmid(Integer crmid) {
        this.crmid = crmid;
    }

    public String getKfname() {
        return kfname;
    }

    public void setKfname(String kfname) {
        this.kfname = kfname == null ? null : kfname.trim();
    }

    public String getKfphone() {
        return kfphone;
    }

    public void setKfphone(String kfphone) {
        this.kfphone = kfphone == null ? null : kfphone.trim();
    }

    public String getKffixphone() {
        return kffixphone;
    }

    public void setKffixphone(String kffixphone) {
        this.kffixphone = kffixphone == null ? null : kffixphone.trim();
    }

    public String getKfmail() {
        return kfmail;
    }

    public void setKfmail(String kfmail) {
        this.kfmail = kfmail == null ? null : kfmail.trim();
    }

    public String getKfqq() {
        return kfqq;
    }

    public void setKfqq(String kfqq) {
        this.kfqq = kfqq == null ? null : kfqq.trim();
    }

    public String getWechatcode() {
        return wechatcode;
    }

    public void setWechatcode(String wechatcode) {
        this.wechatcode = wechatcode == null ? null : wechatcode.trim();
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }
}