package com.rowdy.rathod;

import com.rowdy.common_methods.view.IMvpPresenter;

public interface LoginPresenter<V extends LoginMVPView> extends IMvpPresenter<V> {
    public void loginButtonClicked(String userName, String password, String token);
}
