package com.rowdy.common_methods;

public abstract class AbstractTask implements Runnable {
    public AsyncCallback mAsyncCallback;

    public AsyncCallback getmAsyncCallback() {
        return mAsyncCallback;
    }

    public void setmAsyncCallback(AsyncCallback mAsyncCallback) {
        this.mAsyncCallback = mAsyncCallback;
    }
}
