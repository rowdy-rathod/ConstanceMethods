package com.rowdy.common_methods.view;


/**
 * Every presenter in the app must either implement this interface or extend AbstractPresenter
 * indicating the IMvpView type that wants to be attached with.
 */
public interface IMvpPresenter<V extends IMvpView> {

    void onAttach(V mvpView);

    void onDetach();
}
