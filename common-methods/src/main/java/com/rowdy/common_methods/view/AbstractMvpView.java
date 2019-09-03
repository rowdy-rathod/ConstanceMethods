package com.rowdy.common_methods.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;


public abstract class AbstractMvpView extends ViewGroup implements AdvanceMvpView {

    private IMvpView mParentMvpView;

    public AbstractMvpView(Context context) {
        super(context);
    }

    public AbstractMvpView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbstractMvpView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public AbstractMvpView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void attachParentMvpView(IMvpView mvpView) {
        mParentMvpView = mvpView;
    }

    @Override
    public void hideProgressDialog() {
        if (mParentMvpView != null) {
            mParentMvpView.hideProgressDialog();
        }
    }

    @Override
    public void hideAlertDialog() {
        if (mParentMvpView != null) {
            mParentMvpView.hideAlertDialog();
        }
    }

    @Override
    public void hideKeyboard() {
        if (mParentMvpView != null) {
            mParentMvpView.hideKeyboard();
        }
    }

    @Override
    public boolean isNetworkConnected() {
        return mParentMvpView != null && mParentMvpView.isNetworkConnected();
    }

    protected abstract void bindViewsAndSetOnClickListeners();

    protected abstract void setUp();
}
