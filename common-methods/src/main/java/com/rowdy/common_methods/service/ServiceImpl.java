package com.rowdy.common_methods.service;

import android.content.Context;


import com.rowdy.common_methods.AsyncCallback;
import com.rowdy.common_methods.utils.Enums;

import java.util.Map;

public class ServiceImpl implements Service {
    private static ServiceImpl instance;
    private Context context;
    private ServiceImpl() {}

    public static ServiceImpl getInstance(Context context) {
        if(instance != null) {
            return instance;
        }
        instance =  new ServiceImpl();
        instance.setContext(context);
        return instance;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public AbstractCloudTask loginTask(Map<String, Object> nameValueParams, AsyncCallback callback) {
        AbstractCloudTask loginTask = new DTCloudTask(callback);
        loginTask.setApi(CloudConstant.WS_LOGIN);
        loginTask.setRequestType(Enums.RequestType.POST.name());
        return loginTask;
    }
}
