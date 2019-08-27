package com.lz.rxretrofit.bean;

public class UpdateBean {
    private Integer uid;
    private String maxVersion;
    private boolean isForceUpdate;
    private String content;
    private String forceVersion;
    private String pubishTime;
    private String updateUrl;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getMaxVersion() {
        return maxVersion;
    }

    public void setMaxVersion(String maxVersion) {
        this.maxVersion = maxVersion;
    }

    public boolean isForceUpdate() {
        return isForceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        isForceUpdate = forceUpdate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getForceVersion() {
        return forceVersion;
    }

    public void setForceVersion(String forceVersion) {
        this.forceVersion = forceVersion;
    }

    public String getPubishTime() {
        return pubishTime;
    }

    public void setPubishTime(String pubishTime) {
        this.pubishTime = pubishTime;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }
}
