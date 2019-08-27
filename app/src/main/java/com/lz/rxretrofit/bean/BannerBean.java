package com.lz.rxretrofit.bean;

import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

import java.io.Serializable;

public class BannerBean extends SimpleBannerInfo implements Serializable {
    private String link;
    private String poster;
    public BannerBean(String poster){
        this.poster = poster;
    }

    @Override
    public String getXBannerTitle() {
        return poster;
    }

    @Override
    public Object getXBannerUrl() {
        return poster;
    }
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
