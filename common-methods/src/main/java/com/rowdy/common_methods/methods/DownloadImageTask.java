package com.rowdy.common_methods.methods;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.rowdy.common_methods.R;

import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView bmImage;
    private ProgressBar progressBar;
    private Context context;
    private CircleImageView circleImageView;

    public DownloadImageTask(Context context, ImageView bmImage, ProgressBar progressBar) {
        this.context = context;
        this.bmImage = bmImage;
        this.progressBar = progressBar;
    }

    public DownloadImageTask(Context context, CircleImageView bmImage, ProgressBar progressBar) {
        this.context = context;
        this.circleImageView = bmImage;
        this.progressBar = progressBar;
    }

    public DownloadImageTask(Context context, ImageView bmImage) {
        this.context = context;
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        if (result == null) {
            if (bmImage != null) {
                bmImage.setImageDrawable(context.getResources().getDrawable(R.drawable.product_no_image));
            }
            if (circleImageView != null) {
                circleImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.product_no_image));
            }
        } else {
            if (bmImage != null) {
                bmImage.setImageBitmap(result);
            }
            if (circleImageView != null) {
                circleImageView.setImageBitmap(result);
            }
        }
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }
}
