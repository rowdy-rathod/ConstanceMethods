package com.rowdy.common_methods.view;


import android.content.DialogInterface;

import androidx.annotation.StringRes;

/**
 * Base interface that any class that wants to act as a View in the MVP (Model View Presenter)
 * pattern must implement. Generally this interface will be extended by a more specific interface
 * that then usually will be implemented by an Activity or Fragment.
 */
public interface IMvpView {

    void showProgressDialog(String message);

    void showProgressDialog(int resId);

    void hideProgressDialog();

    void hideAlertDialog();

    void showErrorDialog(Exception e);

    void showErrorDialog(Exception e, DialogInterface.OnDismissListener listener);

    void showToast(String message);

    void showToast(@StringRes int resId);

    void showErrorMessage(int resourceId);

    void showErrorMessage(String message);

    void showErrorMessage(Exception ex);

    boolean isNetworkConnected();

    void hideKeyboard();

}
