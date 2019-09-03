package com.rowdy.common_methods.service;

import android.util.Log;


import com.rowdy.common_methods.AbstractTask;
import com.rowdy.common_methods.utils.Utils;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class AbstractCloudTask extends AbstractTask {
    private static final String LOG_TAG = AbstractCloudTask.class.getSimpleName();
    private String requestType;
    private String baseUrl;
    private String api;
    private Object payload;
    private boolean authenticate = true;
    protected HttpURLConnection connection;

    protected AbstractCloudTask() {
    }


    public String getBaseUrl() {
        return this.baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getApi() {
        return this.api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public Object getPayload() {
        return this.payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public String getRequestType() {
        return this.requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public boolean authenticate() {
        return this.authenticate;
    }

    public void setAuthenticate(boolean authenticate) {
        this.authenticate = authenticate;
    }

    public URL getUrl() throws MalformedURLException {
        String api = this.getApi();
        return Utils.isNullOrEmpty(api) ? new URL(this.getBaseUrl()) : new URL(this.getBaseUrl() + api);
    }


    protected void logTheHeaders(Map<String, List<String>> headers) {
        if (headers != null) {
            StringBuilder builder = new StringBuilder();
            Set<String> keySet = headers.keySet();
            builder.append("{ ");
            Iterator var4 = keySet.iterator();

            while(var4.hasNext()) {
                String key = (String)var4.next();
                builder.append("\t").append(key).append(": ");
                List<String> list = (List)headers.get(key);
                Iterator var7 = list.iterator();

                while(var7.hasNext()) {
                    String str = (String)var7.next();
                    builder.append(str).append("\n");
                }
            }

            builder.append(" }");
            Log.d(LOG_TAG, builder.toString());
        }

    }

    public abstract void setAuthenticationInfo(HttpURLConnection var1);

    public abstract void handleErrorData(int var1, String var2);
}
