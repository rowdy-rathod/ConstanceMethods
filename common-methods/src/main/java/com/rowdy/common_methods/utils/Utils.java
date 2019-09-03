package com.rowdy.common_methods.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


import com.rowdy.common_methods.BaseException;
import com.rowdy.common_methods.DTException;
import com.rowdy.common_methods.R;

import java.math.BigInteger;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.security.MessageDigest;

public class Utils {

    public static String getAppVersion(Context context) {
        PackageInfo packageInfo;
        String versionName = "Unknown";
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return versionName;
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert manager != null;
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if(activeNetwork != null) {
            return activeNetwork.isConnected();
        }
        return false;
    }

    public static boolean isNullOrEmpty(String toCheck) {
        return toCheck == null || toCheck.equalsIgnoreCase("");
    }

    public static void openUrl(Activity activity, String url) {
        if (!isNullOrEmpty(url)) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            Intent chooserIntent = Intent.createChooser(intent, activity.getString(R.string.opens_with));
            if (chooserIntent.resolveActivity(activity.getPackageManager()) != null) {
                activity.startActivity(chooserIntent);
            } else {
                AlertHelper.showToast(activity, activity.getString(R.string.error_no_application_found));
            }
        }
    }
    public static float convertDpToPx(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    //animation


    public static void hideKeyboard(Context context, View view) {
        @SuppressLint("WrongConstant") InputMethodManager imm = (InputMethodManager)context.getSystemService("input_method");
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    public static String getExceptionMessage(Context context, Exception ex) {
        if (ex instanceof BaseException) {
            BaseException exp = (BaseException) ex;
            if (exp.getException() != null && (exp.getException() instanceof ConnectException ||
                    exp.getException() instanceof SocketTimeoutException)) {
                return context.getString(R.string.error_no_host_connection);
            } else if (exp.getException() != null && exp.getException() instanceof DTException) {
                DTException gtException = (DTException) exp.getException();
                if (!isNullOrEmpty(gtException.getMessage())) {
                    return gtException.getMessage();
                }
            }
        } else if (ex instanceof DTException) {
            String message = ex.getMessage();
            if (!Utils.isNullOrEmpty(message)) {
                return message;
            }
        }
        return context.getString(R.string.error_technical_issues);
    }

    public static String getDeviceId(Context context){
        String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        String android_id2 = generateMD5HashString(context.getPackageName() + android_id);
        return android_id2.substring(android_id2.length() - Constant.TOKEN_LENGTH).trim();
    }
    public static String generateMD5HashString(String inputString){
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(inputString.getBytes());
            String hashedPass = new BigInteger(1, md.digest()).toString(16);
            return hashedPass;
        } catch (Exception e){
            return null;
        }
    }

}
