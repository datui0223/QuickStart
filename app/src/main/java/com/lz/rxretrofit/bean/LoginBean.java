package com.lz.rxretrofit.bean;

public class LoginBean {
    private String states;
    private String token;
    private String bindToken;
    private String phoneNo;

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBindToken() {
        return bindToken;
    }

    public void setBindToken(String bindToken) {
        this.bindToken = bindToken;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "states='" + states + '\'' +
                ", token='" + token + '\'' +
                ", bindToken='" + bindToken + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                '}';
    }
}
