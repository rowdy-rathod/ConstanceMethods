package com.rowdy.common_methods.service;

public class ResponseString implements ResponseObject {
    private String data;

    public ResponseString(String data) {
        this.setData(data);
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return this.data;
    }
}