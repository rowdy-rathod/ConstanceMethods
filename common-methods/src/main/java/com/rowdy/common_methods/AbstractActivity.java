package com.rowdy.common_methods;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.rowdy.common_methods.utils.AlertHelper;
import com.rowdy.common_methods.utils.Constant;
import com.rowdy.common_methods.utils.Utils;
import com.rowdy.common_methods.view.IMvpView;


public class AbstractActivity extends AppCompatActivity implements IMvpView {

    final static String CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    IntentFilter intentFilter;
    BroadcastReceiver networkReceiver;
    ProgressDialog progress;
    Dialog dialog;
    Dialog progressDialog;
    public boolean isUIUpdatable = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        networkReceiver = new NetworkChangeReceiver();
        registerReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isUIUpdatable = true;
        registerReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isUIUpdatable = false;
        unRegisterReceiver();
    }

    public void setToolbar(Toolbar toolbar, int drawableId, String title) {
        toolbar.setNavigationIcon(drawableId);
        toolbar.setContentInsetStartWithNavigation(0);
        TextView mTitle = toolbar.findViewById(R.id.mToolbarTitle);
        mTitle.setText(title);
        enableHomeAsUp(true);
        setSupportActionBar(toolbar);
        showToolbarTitle(false);
    }

    public void setToolbar(Toolbar toolbar) {
        toolbar.setContentInsetStartWithNavigation(0);
        TextView mTitle = toolbar.findViewById(R.id.mToolbarTitle);
        mTitle.setVisibility(View.GONE);
        mTitle.setText("");
        setSupportActionBar(toolbar);
        showToolbarTitle(false);
    }


    public void setToolbar(Toolbar toolbar, String title) {
        toolbar.setContentInsetStartWithNavigation(0);
        TextView mTitle = toolbar.findViewById(R.id.mToolbarTitle);
        mTitle.setText(title);
        setSupportActionBar(toolbar);
        showToolbarTitle(false);
    }

    public void setToolbar(Toolbar toolbar, int drawableId) {
        toolbar.setNavigationIcon(drawableId);
        toolbar.setContentInsetStartWithNavigation(0);
        TextView mTitle = toolbar.findViewById(R.id.mToolbarTitle);
        mTitle.setText("");
        setSupportActionBar(toolbar);
        showToolbarTitle(false);
        enableHomeAsUp(true);
    }

    public void enableHomeAsUp(boolean flag) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(flag);
        }
    }

    public void showToolbarTitle(boolean showTitle) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(showTitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void call() {
        int checkPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    Constant.CALL_PERMISSION_INTENT);
        } else {
            String uri = "tel:" + "8983765316";
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse(uri));
            startActivity(intent);
        }
    }

    public void getPermission() {
        int checkPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    Constant.STORAGE_PERMISSION_INTENT);
        } else {
            permissionGrant();
        }
    }

    public void permissionGrant() {

    }

    public void sendWhatsappMessage(String number) {
        Uri uri = Uri.parse("smsto:" + number);
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        i.putExtra("sms_body", getString(R.string.whatsapp_primary_text));
        i.setPackage("com.whatsapp");
        startActivity(i);
    }

    public void showCirculerProgressDialog() {
        progress = AlertHelper.createProgressDialog(this);
        progress.show();
    }

    public void hideCirculerProgressDialog() {
        if (progress != null) {
            progress.dismiss();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == Constant.STORAGE_PERMISSION_INTENT) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permissionGrant();
            } else {
                AlertHelper.showErrorDialog(this, getString(R.string.permission_denied), getString(R.string.error_permission_denied), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getPermission();
                    }
                });
            }
            return;
        }
        if (requestCode == Constant.CALL_PERMISSION_INTENT) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                call();
            }
            return;
        }
    }

    protected void registerReceiver() {
        registerReceiver(networkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    protected void unRegisterReceiver() {
        try {
            unregisterReceiver(networkReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void onConnect() {
        if (dialog != null) {
            dialog.dismiss();
        }
        // Toast.makeText(AbstractActivity.this, "Connected....", Toast.LENGTH_SHORT).show();
    }

    public void onDisConnect() {
        showNetworkErrorDialog();
    }

    public void showNetworkErrorDialog() {
        dialog = AlertHelper.showNetworkErrorDialog(this, getString(R.string.error_network), getString(R.string.error_network),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertHelper.dismissDialog(dialog);
                    }
                });
    }

//    public class NetworkChangeReceiver extends BroadcastReceiver {
//        public NetworkChangeReceiver() {
//        }
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            try {
//                if (Utils.isNetworkConnected(context)) {
//                    Log.e("DT", "Online Connect Intenet ");
//                    //callback.onConnect();
//                    onConnect();
//
//
//                } else {
//                    Log.e("DT", "Conectivity Failure !!! ");
//                    //callback.onDisConnect();
//                    onDisConnect();
//                }
//            } catch (NullPointerException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    private class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            String actionOfIntent = intent.getAction();
            boolean isConnected = Utils.isNetworkConnected(context);
            assert actionOfIntent != null;
            if (actionOfIntent.equals(CONNECTIVITY_ACTION)) {
                if (isConnected) {
                    onConnect();
                } else {
                    onDisConnect();
                }
            }
        }
    }

    public boolean isUIUpdateAble() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return !this.isFinishing() || !isDestroyed();
        }
        return false;
    }

    //MVP Override methods


    @Override
    public void showProgressDialog(final String message) {
        if (isUIUpdateAble()) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog = AlertHelper.showProgressDialog(AbstractActivity.this, message, null);
                }
            });
        }
    }

    @Override
    public void showProgressDialog(int resId) {
        showProgressDialog(getString(resId));
    }

    @Override
    public void hideProgressDialog() {
        if (isUIUpdateAble()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertHelper.dismissDialog(progressDialog);
                }
            });
        }
    }

    @Override
    public void hideAlertDialog() {
        if (isUIUpdateAble()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertHelper.dismissDialog(dialog);
                }
            });
        }
    }

    @Override
    public void hideKeyboard() {
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = this.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        Utils.hideKeyboard(this, view);
    }

    @Override
    public void showErrorMessage(Exception ex) {
        String message = Utils.getExceptionMessage(this, ex);
        showErrorMessage(message);
    }

    @Override
    public void showErrorMessage(int resourceId) {
        if (isUIUpdateAble()) {
            String message = getString(resourceId);
            showErrorMessage(message);
        }
    }

    @Override
    public void showErrorMessage(final String message) {
        if (isUIUpdateAble()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog = AlertHelper.showErrorDialog(AbstractActivity.this,
                            getString(R.string.alert_title_error), message, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    hideAlertDialog();
                                }
                            });
                }
            });
        }
    }

    @Override
    public void showErrorDialog(final Exception e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                displayErrorDialog(e, null);
            }
        });
    }

    @Override
    public void showErrorDialog(final Exception e, final DialogInterface.OnDismissListener listener) {
        if (isUIUpdateAble()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    displayErrorDialog(e, listener);
                }
            });
        }
    }

    @Override
    public void showToast(final String message) {
        if (isUIUpdateAble()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertHelper.showToast(AbstractActivity.this, message);
                }
            });
        }
    }

    @Override
    public void showToast(int resId) {
        showToast(getString(resId));
    }

    @Override
    public boolean isNetworkConnected() {
        return Utils.isNetworkConnected(this);
    }

    protected void displayErrorDialog(Exception ex, final DialogInterface.OnDismissListener onDismissListener) {
        String message = getString(R.string.alert_title_error);
        if (ex != null) {
            if (Utils.isNullOrEmpty(ex.getMessage())) {
                message = ex.getMessage();
            }
        }
        AlertHelper.showErrorDialog(this, getString(R.string.alert_title_error),
                message, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (onDismissListener != null) {
                            onDismissListener.onDismiss(dialog);
                        }
                    }
                });
    }
}
