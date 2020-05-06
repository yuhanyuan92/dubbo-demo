package com.qianlima.application.domain.qlmservice;

public class ZdyVipMatchWithBLOBs extends ZdyVipMatch {
    private String areanames;

    private String yewu;

    public String getAreanames() {
        return areanames;
    }

    public void setAreanames(String areanames) {
        this.areanames = areanames == null ? null : areanames.trim();
    }

    public String getYewu() {
        return yewu;
    }

    public void setYewu(String yewu) {
        this.yewu = yewu == null ? null : yewu.trim();
    }
}