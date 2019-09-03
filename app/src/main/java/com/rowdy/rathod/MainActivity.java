package com.rowdy.rathod;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;

import com.rowdy.common_methods.AbstractActivity;

public class MainActivity extends AbstractActivity implements LoginMVPView {

    LoginPresenter<MainActivity> loginPresenter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            super.onCreate(savedInstanceState);
        }
        setContentView(R.layout.activity_main);
        loginPresenter = new LoginPresenterImpl<>(this);
        loginPresenter.onAttach(this);
    }

    @Override
    public void loadDashboard() {

    }
}
