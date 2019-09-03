package com.rowdy.common_methods.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rowdy.common_methods.R;


public final class AlertHelper {
    private AlertHelper() {
    }

    private static AlertDialog.Builder getAlertDialogBuilder(Context context) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert);
        else
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Holo_Light);
        return builder;
    }

    public static Dialog showAlertDialogWithView(Context context, View view, String title, boolean isCancelable) {
        try {
            AlertDialog.Builder builder = getAlertDialogBuilder(context);
            if (!Utils.isNullOrEmpty(title)) {
                builder.setTitle(title);
            }
            builder.setView(view);
            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(isCancelable);
            dialog.show();
            return dialog;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public static void dismissDialog(DialogInterface dialog) {
        try {
            if (dialog != null) {
                dialog.dismiss();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void showToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 10);
        TextView textView = toast.getView().findViewById(android.R.id.message);
        if (textView != null) {
            textView.setGravity(Gravity.CENTER);
        }
        toast.show();
    }

    public static Dialog showDefaultAlertDialog(Context context, String title, String message, DialogInterface.OnClickListener positiveBtnListener) {
        return showAlertDialog(context, title, message, context.getString(R.string.close), positiveBtnListener,
                null, null);
    }

    public static Dialog showAlertDialog(Context context, String title, String message, String positiveBtnTitle,
                                         DialogInterface.OnClickListener positiveBtnListener, String negativeBtnTitle,
                                         DialogInterface.OnClickListener negativeBtnListener) {
        return showCustomDialog(context, Enums.AlertType.ALERT, title, message, positiveBtnTitle, positiveBtnListener,
                negativeBtnTitle, negativeBtnListener);
    }

    public static Dialog showErrorDialog(Context context, String title, String message, DialogInterface.OnClickListener positiveBtnListener) {
        return showCustomDialog(context, Enums.AlertType.ERROR, title, message, context.getString(R.string.close), positiveBtnListener,
                null, null);
    }

    public static Dialog showInfoDialog(Context context, String title, String message, DialogInterface.OnClickListener positiveBtnListener) {
        return showCustomDialog(context, Enums.AlertType.INFO, title, message, context.getString(R.string.close), positiveBtnListener,
                null, null);
    }

    public static Dialog showNetworkErrorDialog(Context context, String title, String message, DialogInterface.OnClickListener positiveBtnListener) {
        return showCustomDialog(context, Enums.AlertType.NETWORK_ERROR, title, message, context.getString(R.string.close), positiveBtnListener,
                null, null);
    }

    @SuppressLint("InflateParams")
    public static Dialog showCustomDialog(Context context, Enums.AlertType alertType, String title, String message,
                                          String positiveBtnTitle, final DialogInterface.OnClickListener positiveBtnListener,
                                          String negativeBtnTitle, final DialogInterface.OnClickListener negativeBtnListener) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            AlertDialog.Builder builder = getAlertDialogBuilder(context);
            final AlertDialog dialog = builder.create();

            View view = inflater.inflate(R.layout.custom_dialog_layout, null);
            if (view != null) {
                ImageView titleImageView = view.findViewById(R.id.title_image_view);
                TextView titleTextView = view.findViewById(R.id.title_text_view);
                TextView messageTextView = view.findViewById(R.id.message_text_view);
                ViewGroup positiveButtonContainer = view.findViewById(R.id.positive_container);
                Button positiveButton = view.findViewById(R.id.positive_button);
                ViewGroup negativeButtonContainer = view.findViewById(R.id.negative_container);
                Button negativeButton = view.findViewById(R.id.negative_button);

                if (alertType != null) {
                    titleImageView.setImageResource(IconFactory.getIconForAlertType(alertType));
                } else {
                    titleImageView.setVisibility(View.GONE);
                }
                if (!Utils.isNullOrEmpty(title)) {
                    titleTextView.setText(title);
                    titleTextView.setTypeface(Typefaces.getInstance(context).getTypeface(Typefaces.Style.REGULAR));
                } else {
                    titleTextView.setVisibility(View.GONE);
                }
                if (!Utils.isNullOrEmpty(message)) {
                    messageTextView.setText(message);
                    messageTextView.setTypeface(Typefaces.getInstance(context).getTypeface(Typefaces.Style.REGULAR));
                } else {
                    messageTextView.setVisibility(View.GONE);
                }
                if (!Utils.isNullOrEmpty(positiveBtnTitle)) {
                    positiveButton.setText(positiveBtnTitle);

                    positiveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (positiveBtnListener != null) {
                                positiveBtnListener.onClick(dialog, 0);
                            }
                        }
                    });
                    positiveButton.setTypeface(Typefaces.getInstance(context).getTypeface(Typefaces.Style.BOLD));
                } else {
                    positiveButtonContainer.setVisibility(View.GONE);
                }
                if (!Utils.isNullOrEmpty(negativeBtnTitle)) {
                    negativeButton.setText(negativeBtnTitle);
                    negativeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (negativeBtnListener != null) {
                                negativeBtnListener.onClick(dialog, 0);
                            }
                        }
                    });
                    negativeButton.setTypeface(Typefaces.getInstance(context).getTypeface(Typefaces.Style.BOLD));
                } else {
                    negativeButtonContainer.setVisibility(View.GONE);
                }

                dialog.setView(view);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                dialog.show();
                return dialog;
            }
        }
        return null;
    }

    public static ProgressDialog createProgressDialog(Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {

        }
        dialog.setCancelable(false);
        dialog.getWindow()
                .setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_progressbar);
        dialog.setCancelable(true);
        // dialog.setMessage(Message);
        return dialog;
    }

    @SuppressLint("InflateParams")
    public static Dialog showProgressDialog(Context context, String title, DialogInterface.OnCancelListener listener) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            AlertDialog.Builder builder = getAlertDialogBuilder(context);
            final AlertDialog dialog = builder.create();

            View view = inflater.inflate(R.layout.custom_progress_dialog_layout, null);
            if (view != null) {
                TextView titleTextView = view.findViewById(R.id.title_text_view);
                if (!Utils.isNullOrEmpty(title)) {
                    titleTextView.setText(title);
                    titleTextView.setTypeface(Typefaces.getInstance(context).getTypeface(Typefaces.Style.REGULAR));
                } else {
                    titleTextView.setVisibility(View.GONE);
                }
                dialog.setCanceledOnTouchOutside(false);
                if (listener != null) {
                    dialog.setOnCancelListener(listener);
                    dialog.setCancelable(true);
                } else {
                    dialog.setCancelable(false);
                }
                dialog.setView(view);
                dialog.show();
                return dialog;
            }
        }
        return null;
    }

}
