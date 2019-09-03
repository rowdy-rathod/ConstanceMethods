package com.rowdy.common_methods.client;


import com.rowdy.common_methods.AbstractTask;
import com.rowdy.common_methods.AsyncCallback;
import com.rowdy.common_methods.service.Service;

public interface Client {
    public String getBaseUrl();
    public Service getService();

    public void saveUserDetails(String userName, String password, String token);
    public String getUserName();

    //API
    AbstractTask login(String userName, String password, String token, final AsyncCallback callback);

}
