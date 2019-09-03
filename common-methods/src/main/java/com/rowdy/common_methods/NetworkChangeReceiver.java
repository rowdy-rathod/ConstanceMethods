package com.rowdy.common_methods;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Observable;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private static NetworkObservable observable;

    public void onReceive(Context context, Intent intent) {
        getObservable().connectionChanged();
    }

    public static class NetworkObservable extends Observable {
        public void connectionChanged() {
            setChanged();
            notifyObservers();
        }
    }

    public static NetworkObservable getObservable() {
        if (observable == null) {
            observable = new NetworkObservable();
        }

        return observable;
    }
}