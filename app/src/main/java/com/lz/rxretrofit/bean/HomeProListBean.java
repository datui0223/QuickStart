package com.lz.rxretrofit.bean;

import java.util.List;

public class HomeProListBean {
    private List<MarketGoodBean> data;
    private Integer page_no;
    private Integer page_size;
    private Integer data_total;

    public List<MarketGoodBean> getData() {
        return data;
    }

    public void setData(List<MarketGoodBean> data) {
        this.data = data;
    }

    public Integer getPage_no() {
        return page_no;
    }

    public void setPage_no(Integer page_no) {
        this.page_no = page_no;
    }

    public Integer getPage_size() {
        return page_size;
    }

    public void setPage_size(Integer page_size) {
        this.page_size = page_size;
    }

    public Integer getData_total() {
        return data_total;
    }

    public void setData_total(Integer data_total) {
        this.data_total = data_total;
    }
}
