package com.rowdy.common_methods.client;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.rowdy.common_methods.AbstractTask;
import com.rowdy.common_methods.AsyncCallback;
import com.rowdy.common_methods.DTApplication;
import com.rowdy.common_methods.DTException;
import com.rowdy.common_methods.client.task.LoginTask;
import com.rowdy.common_methods.models.User;
import com.rowdy.common_methods.service.CloudConstant;
import com.rowdy.common_methods.service.Service;
import com.rowdy.common_methods.service.ServiceImpl;
import com.rowdy.common_methods.utils.Constant;


public class ClientImpl implements Client {

    private static ClientImpl instance;
    private Context context;
    private Service service;

    private ClientImpl() {
    }

    private User user;

    public static ClientImpl getInstance(Context context) {
        if (instance != null) {
            return instance;
        }
        instance = new ClientImpl();
        instance.setContext(context);
        instance.setService(ServiceImpl.getInstance(context));
        return instance;
    }

    @Override
    public String getBaseUrl() {
        return Constant.BASE_URL;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public User getUser() {
        return user;
    }

    @Override
    public void saveUserDetails(String userName, String password, String token) {
        SharedPreferences userSharedPref = DTApplication.getAppContext().getSharedPreferences("DTSpf", 0);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = userSharedPref.edit();
        editor.putString(CloudConstant.JSON_KEY_USERNAME, userName); // Storing string
        editor.putString(CloudConstant.JSON_KEY_PASSWORD, password); // Storing string
        editor.putString(CloudConstant.JSON_KEY_TOKEN, token); // Storing string
        editor.apply();
    }

    @Override
    public String getUserName() {
        String name = null;
        SharedPreferences userSharedPref = DTApplication.getAppContext().getSharedPreferences("DTSpf", 0);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = userSharedPref.edit();
        name = userSharedPref.getString(CloudConstant.JSON_KEY_USERNAME, null); // Storing string
        return name;
    }

    //API

    @Override
    public AbstractTask login(String userName, String password, String token, final AsyncCallback callback) {
        Thread loginTask = new LoginTask(this, userName, password, token, new AsyncCallback() {
            @Override
            public void onSuccess(Object object) {
                if (object instanceof User) {
                    user = (User) object;
                    callback.onSuccess(object);
                } else {
                    callback.onFailure(new DTException());
                }
            }

            @Override
            public void onFailure(Exception ex) {
                callback.onFailure(ex);
            }
        });
        loginTask.start();

        return null;
    }
}
