package com.qianlima.application.domain.qianlima;

public class ZdyXmZdWithBLOBs extends ZdyXmZd {

    private String mText;

    private String description;

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText == null ? null : mText.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}