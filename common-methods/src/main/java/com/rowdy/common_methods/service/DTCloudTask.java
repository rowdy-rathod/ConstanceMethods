package com.rowdy.common_methods.service;

import android.os.Build;
import android.util.Log;


import com.rowdy.common_methods.AsyncCallback;
import com.rowdy.common_methods.BaseException;
import com.rowdy.common_methods.DTException;
import com.rowdy.common_methods.utils.Enums;
import com.rowdy.common_methods.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DTCloudTask extends AbstractCloudTask {
    private static final String LOG_TAG = DTCloudTask.class.getSimpleName();
    private AsyncCallback callBack;

    public DTCloudTask(AsyncCallback callback) {
        this.callBack = callback;
    }


    public void run() {
        int responseCode = HttpURLConnection.HTTP_BAD_REQUEST;
        try {
            String encodedUrlString = getUrl().toString().replaceAll(" ", "%20"); //replace all spaces from url with %20
            URL url = new URL(encodedUrlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(getRequestType());
//            connection.setRequestProperty(CoreCloudConstants.KEY_USER_AGENT, GTCloudConstants.VALUE_USER_AGENT +
//                    CoreConstants.SPACE + Utilities.getAppVersion());
//            if(authenticate()) {
//                setAuthenticationInfo(connection);
//            }
            Log.d(LOG_TAG, "Request URL: " + url.toString());
            Log.d(LOG_TAG, "Request Type: " + getRequestType());
            String requestPayload = null;
            if(getPayload() != null) {
                    requestPayload = getPayload().toString();
            }
            Log.d(LOG_TAG, "Request Payload: " + requestPayload);

            if((getRequestType().equalsIgnoreCase(Enums.RequestType.POST.name()) ||
                    getRequestType().equalsIgnoreCase(Enums.RequestType.PUT.name()) ||
                    getRequestType().equalsIgnoreCase(Enums.RequestType.DELETE.name())) &&
                    !Utils.isNullOrEmpty(requestPayload)) {
                connection.setRequestProperty(CloudConstant.KEY_CONTENT_TYPE, CloudConstant.CONTENT_TYPE_JSON);
                connection.setDoOutput(true);
                DataOutputStream os = new DataOutputStream(connection.getOutputStream());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    os.write(requestPayload.getBytes(StandardCharsets.UTF_8));
                }
                os.flush();
                os.close();
            }
            connection.setConnectTimeout(CloudConstant.CONNECTION_TIMEOUT_CRITICAL_OPS);
            connection.setReadTimeout(CloudConstant.CONNECTION_TIMEOUT_CRITICAL_OPS);
            connection.connect();
            try {
                responseCode = connection.getResponseCode();
                Log.d(LOG_TAG, "Response Code: " + responseCode);
            } catch (IOException ex) {
                responseCode = connection.getResponseCode();
            }

            Log.d(LOG_TAG, ">>>>>>> Response Headers: " + url.toString());

            String responseMessage = connection.getResponseMessage();
            Log.d(LOG_TAG, "Response Message: " + responseMessage);

            handleResponse(responseCode);
        } catch (Exception ex) {
            ex.printStackTrace();
            getmAsyncCallback().onFailure(ex);
        } finally {
            if(connection != null) {
                connection.disconnect();
                connection = null;
            }
        }
    }

//    public void setAuthenticationInfo(HttpURLConnection connection) {
//        if(nameValueParams.containsKey(CoreCloudConstants.AUTH_TAG)) {
//            ServiceAuthInfo serviceAuthInfo = (ServiceAuthInfo) nameValueParams.get(CoreCloudConstants.AUTH_TAG);
//            if(serviceAuthInfo != null) {
//                if(!StringUtils.isNullOrEmpty(serviceAuthInfo.getBasicAuthToken())) {
//                    connection.setRequestProperty(GTCloudConstants.KEY_AUTHORIZATION,
//                            GTCloudConstants.KEY_BASIC + CoreConstants.SPACE + serviceAuthInfo.getBasicAuthToken());
//                }
//            }
//        }
//    }

    private void handleResponse(int responseCode) throws Exception {
        String responseData;
        if (responseCode != HttpURLConnection.HTTP_OK &&
                responseCode != HttpURLConnection.HTTP_CREATED) {
            handleErrorResponse(responseCode);
            return;
        }
        responseData = CloudServiceHelper.toString(connection.getInputStream());
        Log.d(LOG_TAG, "Response Success Data: " + responseData);
        callBack.onSuccess(new ResponseString(responseData));
    }

    private void handleErrorResponse(int responseCode) throws Exception {
        String errorData;
        if(responseCode == HttpURLConnection.HTTP_MOVED_PERM || responseCode == HttpURLConnection.HTTP_MOVED_TEMP) {
            if(!Utils.isNullOrEmpty(connection.getHeaderField(CloudConstant.HEADER_FIELD_LOCATION))) {
//                setBaseUrl(connection.getHeaderField(CoreCloudConstants.HEADER_FIELD_LOCATION));
//                setApi(CoreConstants.EMPTY);
                connection.disconnect();
                connection = null;
                run();
            }
        } else {
            try {
                errorData = CloudServiceHelper.toString(connection.getErrorStream());
            } catch (Exception ex) {
                errorData = CloudServiceHelper.toString(connection.getInputStream());
            }
            Log.d(LOG_TAG, "Response Error Data: " + errorData);
            handleErrorData(responseCode, errorData);
        }
    }

    @Override
    public void setAuthenticationInfo(HttpURLConnection var1) {

    }

    public void handleErrorData(int responseCode, String errorData) {
        try {
            BaseException exception = new BaseException();
            boolean isValidJsonFormat = isValidJSONFormat(errorData);
            if(isValidJsonFormat) {
                DTException gtException = new DTException();
                gtException.setPropertiesFromString(errorData);
                exception.setException(gtException);
            } else {
                exception.setStatusCode(responseCode);
                exception.setResponseObject(new ResponseString(errorData));
            }
            callBack.onFailure(exception);
        } catch (Exception ex) {

        }
    }

    private void throwException(int statusCode, Exception ex) {
        BaseException exception = new BaseException(ex);
        exception.setStatusCode(statusCode);
        callBack.onFailure(exception);
    }

    private boolean isValidJSONFormat(String responseMsg) {
        boolean isValidJSONFormat = false;
        try {
            new JSONObject(responseMsg);
            isValidJSONFormat = true;
        } catch(JSONException ex) {
//            ex.printStackTrace();
        }
        return isValidJSONFormat;
    }
}
