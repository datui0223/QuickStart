package com.lz.rxretrofit.bean;

public class BaseErrorBean {
    private String message;
    private String code;

    public BaseErrorBean(){
        super();
    }
    public BaseErrorBean (String message,String code){
        this.code = code;
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
