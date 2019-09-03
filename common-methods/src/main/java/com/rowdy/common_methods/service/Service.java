package com.rowdy.common_methods.service;



import com.rowdy.common_methods.AsyncCallback;

import java.util.Map;

public interface Service {
    AbstractCloudTask loginTask(Map<String, Object> nameValueParams, AsyncCallback callback);
}
