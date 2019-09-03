package com.rowdy.common_methods.client.task;

import android.util.Log;


import com.rowdy.common_methods.AsyncCallback;
import com.rowdy.common_methods.DTException;
import com.rowdy.common_methods.client.Client;
import com.rowdy.common_methods.models.User;
import com.rowdy.common_methods.service.AbstractCloudTask;
import com.rowdy.common_methods.service.CloudConstant;
import com.rowdy.common_methods.service.ResponseString;
import com.rowdy.common_methods.utils.Utils;

import org.json.JSONObject;

public class LoginTask extends Thread {
    private Client client;
    private String userName;
    private String password;
    private String token;
    private AsyncCallback mAssyncCallback;

    public LoginTask(Client client, String userName, String password, String token, AsyncCallback mAssyncCallback) {
        this.client = client;
        this.userName = userName;
        this.password = password;
        this.token = token;
        this.mAssyncCallback = mAssyncCallback;
    }

    @Override
    public void run() {
        Log.d("DT", LoginTask.class.getSimpleName());
        String url = client.getBaseUrl();
        if (!Utils.isNullOrEmpty(url)) {
            try {
                JSONObject payloadObject = new JSONObject();
                payloadObject.put(CloudConstant.JSON_KEY_USERNAME, userName);
                payloadObject.put(CloudConstant.JSON_KEY_PASSWORD, password);
                payloadObject.put(CloudConstant.JSON_KEY_TOKEN, token);

                AsyncCallback callback = new AsyncCallback() {
                    @Override
                    public void onSuccess(Object object) {
                        if (object instanceof ResponseString) {
                            String response = ((ResponseString) object).getData();
                            if (!Utils.isNullOrEmpty(response)) {
                                try {
                                    User user = new User();
                                    if (user.setPropertiesFromString(response) == true) {
                                        user.setPropertiesFromString(response);
                                        mAssyncCallback.onSuccess(user);
                                    } else {
                                        mAssyncCallback.onFailure(new DTException("Invalid user email/password."));
                                    }
                                } catch (Exception ex) {
                                    mAssyncCallback.onFailure(new DTException());
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Exception ex) {
                        mAssyncCallback.onFailure(ex);
                    }
                };

                AbstractCloudTask cloudTask = client.getService().loginTask(null, callback);
                cloudTask.setBaseUrl(url);
                cloudTask.setPayload(payloadObject);
                cloudTask.run();
            } catch (Exception ex) {
                mAssyncCallback.onFailure(ex);
            }
        }

    }
}
