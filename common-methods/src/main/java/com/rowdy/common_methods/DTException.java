package com.rowdy.common_methods;


import com.rowdy.common_methods.service.ResponseObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DTException extends Exception {
    private int code;
    private int subCode;
    private String message;
    private ResponseObject responseObject;

    private static final String JSON_KEY_CODE = "code";
    private static final String JSON_KEY_SUB_CODE = "subCode";
    private static final String JSON_KEY_MESSAGE = "message";

    public DTException() {
    }

    public DTException(ResponseObject responseObject) {
        setResponseObject(responseObject);
    }

    public DTException(Exception ex) {
        super(ex);
    }

    public DTException(String message) {
        setMessage(message);
    }

    public DTException(Throwable throwable) {
        super(throwable);
    }

    public DTException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getSubCode() {
        return subCode;
    }

    public void setSubCode(int subCode) {
        this.subCode = subCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseObject getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(ResponseObject responseObject) {
        this.responseObject = responseObject;
    }

    public void setPropertiesFromJSONObject(JSONObject jsonObject) throws JSONException {
        if (jsonObject.has(JSON_KEY_CODE) && !jsonObject.isNull(JSON_KEY_CODE)) {
            code = jsonObject.getInt(JSON_KEY_CODE);
        }
        if (jsonObject.has(JSON_KEY_SUB_CODE) && !jsonObject.isNull(JSON_KEY_SUB_CODE)) {
            subCode = jsonObject.getInt(JSON_KEY_SUB_CODE);
        }
        if (jsonObject.has(JSON_KEY_MESSAGE) && !jsonObject.isNull(JSON_KEY_MESSAGE)) {
            message = jsonObject.getString(JSON_KEY_MESSAGE);
        }
    }

    public void setPropertiesFromJSONArray(JSONArray jsonArray) throws JSONException {
    }

    public void setPropertiesFromString(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        setPropertiesFromJSONObject(jsonObject);
    }
}
