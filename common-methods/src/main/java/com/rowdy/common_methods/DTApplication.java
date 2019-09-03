package com.rowdy.common_methods;

import android.app.Application;
import android.content.Context;

import com.rowdy.common_methods.client.Client;
import com.rowdy.common_methods.client.ClientImpl;

public class DTApplication extends Application {
    private static Context appContext;
    private static Client client;
    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }
    public static Context getAppContext() {
        return appContext;
    }
    private static void initClient() {
        if(client == null) {
            client = ClientImpl.getInstance(getAppContext());
        }
    }
    public static Client getClient() {
        if(client == null) {
            initClient();
        }
        return client;
    }
}
