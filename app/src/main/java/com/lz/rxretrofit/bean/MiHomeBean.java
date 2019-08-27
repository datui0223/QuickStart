package com.lz.rxretrofit.bean;

import java.util.List;

public class MiHomeBean {
    private String background;
    private CreditRecord creditRecord;
    private List<AllProType> categorysSlide;
    private List<BannerBean> banners;
    private String publishState;
    private String mailNumber;
    private String goodsJumpUrl;
    private String vipUrl;

    public List<AllProType> getCategorysSlide() {
        return categorysSlide;
    }

    public void setCategorysSlide(List<AllProType> categorysSlide) {
        this.categorysSlide = categorysSlide;
    }

    public List<BannerBean> getBanners() {
        return banners;
    }

    public void setBanners(List<BannerBean> banners) {
        this.banners = banners;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public CreditRecord getCreditRecord() {
        return creditRecord;
    }

    public void setCreditRecord(CreditRecord creditRecord) {
        this.creditRecord = creditRecord;
    }

    public String getPublishState() {
        return publishState;
    }

    public void setPublishState(String publishState) {
        this.publishState = publishState;
    }

    public String getMailNumber() {
        return mailNumber;
    }

    public void setMailNumber(String mailNumber) {
        this.mailNumber = mailNumber;
    }

    public String getGoodsJumpUrl() {
        return goodsJumpUrl;
    }

    public void setGoodsJumpUrl(String goodsJumpUrl) {
        this.goodsJumpUrl = goodsJumpUrl;
    }

    public String getVipUrl() {
        return vipUrl;
    }

    public void setVipUrl(String vipUrl) {
        this.vipUrl = vipUrl;
    }

    public class CreditRecord {
        private String amount;
        private String collectionCompletedTime;
        private String reloanTimeWhenReject;
        private String state;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCollectionCompletedTime() {
            return collectionCompletedTime;
        }

        public void setCollectionCompletedTime(String collectionCompletedTime) {
            this.collectionCompletedTime = collectionCompletedTime;
        }

        public String getReloanTimeWhenReject() {
            return reloanTimeWhenReject;
        }

        public void setReloanTimeWhenReject(String reloanTimeWhenReject) {
            this.reloanTimeWhenReject = reloanTimeWhenReject;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }

    @Override
    public String toString() {
        return "MiHomeBean{" +
                "background='" + background + '\'' +
                ", creditRecord=" + creditRecord +
                ", publishState='" + publishState + '\'' +
                ", mailNumber='" + mailNumber + '\'' +
                ", goodsJumpUrl='" + goodsJumpUrl + '\'' +
                ", vipUrl='" + vipUrl + '\'' +
                '}';
    }
}
