package com.rowdy.common_methods;


import com.rowdy.common_methods.service.ResponseObject;

public class BaseException extends Exception {
    private ResponseObject responseObject;
    private int statusCode;
    private Exception ex;

    public BaseException() {
    }

    public BaseException(ResponseObject responseObject, int statusCode) {
        this.setResponseObject(responseObject);
        this.setStatusCode(statusCode);
    }

    public BaseException(String detailMessage) {
        super(detailMessage);
    }

    public BaseException(Throwable throwable) {
        super(throwable);
    }

    public BaseException(Exception ex) {
        this.setException(ex);
    }

    public BaseException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public ResponseObject getResponseObject() {
        return this.responseObject;
    }

    public void setResponseObject(ResponseObject responseObject) {
        this.responseObject = responseObject;
    }

    public Exception getException() {
        return this.ex;
    }

    public void setException(Exception ex) {
        this.ex = ex;
    }
}