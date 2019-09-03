package com.rowdy.rathod;

import com.rowdy.common_methods.AsyncCallback;
import com.rowdy.common_methods.DTApplication;
import com.rowdy.common_methods.utils.Utils;
import com.rowdy.common_methods.view.AbstractPresenter;

public class LoginPresenterImpl<V extends LoginMVPView> extends AbstractPresenter<V>
        implements LoginPresenter<V> {

    LoginMVPView loginMVPView;

    public LoginPresenterImpl(LoginMVPView loginMVPView) {
        this.loginMVPView = loginMVPView;
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
    }

    @Override
    public void loginButtonClicked(String userName, String password, String token) {
        if(isValid(userName, password)) {
            DTApplication.getClient().login(userName, password, token, new AsyncCallback() {
                @Override
                public void onSuccess(Object Object) {
                    loginMVPView.loadDashboard();
                }

                @Override
                public void onFailure(Exception ex) {
                    loginMVPView.showErrorMessage(ex);
                }
            });
        } else {
            loginMVPView.showToast(R.string.error_valid_user_name_and_password);
        }
    }
    public boolean isValid(String userName, String password) {
        return !Utils.isNullOrEmpty(userName) && !Utils.isNullOrEmpty(password);
    }
}
