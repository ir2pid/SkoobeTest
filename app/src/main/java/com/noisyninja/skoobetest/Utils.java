package com.noisyninja.skoobetest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.SyncStateContract;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by sudiptadutta on 23/09/16.
 */
public class Utils {


    private static String TAG = Utils.class.getSimpleName();
    private static ProgressDialog mProgressDialog;

    public static void logD(String s) {
        Log.d(TAG, s);
    }

    public static void showProgress(Context context, Constants.PROGRESS_STYLE progress_style) {
        mProgressDialog = new ProgressDialog(context);
        int style = (progress_style == Constants.PROGRESS_STYLE.INDETERMINATE) ? ProgressDialog.STYLE_SPINNER : ProgressDialog.STYLE_HORIZONTAL;

        mProgressDialog.setMessage(Constants.DOWNLOAD_TEXT);
        mProgressDialog.setProgressStyle(style);
        mProgressDialog.setCancelable(false);
        mProgressDialog.getWindow().getAttributes().windowAnimations = R.style.dialogAnimation_down_up;
        mProgressDialog.show();
    }

    public static void hideProgress() {
        mProgressDialog.dismiss();
    }


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public static Object getFromJson(String data, Class clazz) {
        Object o = null;

        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            o = gson.fromJson(data, clazz);
        } catch (Exception e) {
            //crittercismException(e);
            Log.e(TAG, "Json Error" + e.getMessage());
        }

        return o;
    }

    public static void glideLoad(Context context, ImageView view, String url) {
        Glide.with(context)
                .load(url)
                .sizeMultiplier(1.0f)//fitCenter()
                .placeholder(R.drawable.loading)
                .crossFade()
                .animate(new FlipAnimation(view, view))
                .into(view);
    }

    public static void setHtmlText(TextView textView, String data) {
        if (data != null && data.length() > 1) {
            textView.setText(Html.fromHtml(data));
        }
    }

    public static void makeAnimation(View view, Techniques techniques) {
        YoYo.with(techniques)
                .duration(Constants.ANIMATION_TIME_700)
                .playOn(view);
    }

    public static void makeAnimation(View view) {
        makeAnimation(view, Techniques.SlideInLeft);
    }


    public static void animateFlip(View rootLayout, View cardFace, View cardBack) {
        FlipAnimation flipAnimation = new FlipAnimation(cardFace, cardBack);

        if (cardFace.getVisibility() == View.INVISIBLE) {
            flipAnimation.reverse();
        }
        rootLayout.startAnimation(flipAnimation);
    }

    public static String formatDescription(String text) {
        int start = text.indexOf("<span>");
        int end = text.indexOf("</span>");
        String formatted = text.substring(start, end);
        return formatted;
    }

    public static void openURL(Context context, String url) {
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(myIntent);
    }


    public static String getStringResource(Context context, @StringRes int stringId) {
        return context.getResources().getString(stringId);
    }

    public static void showInfoDialog(final Context context, String title, String message) {
        // custom dialog

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });

        AlertDialog dialog = dialogBuilder.create();

        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialogAnimation_down_up;
        dialog.show();
    }
}
