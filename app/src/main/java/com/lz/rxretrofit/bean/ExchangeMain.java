package com.lz.rxretrofit.bean;

public class ExchangeMain {
    private int currentTab;

    public ExchangeMain(int currentTab){
        this.currentTab = currentTab;
    }

    public int getCurrentTab(){
        return currentTab;
    }

    public void setCurrentTab(int currentTab){
        this.currentTab = currentTab;
    }
}
