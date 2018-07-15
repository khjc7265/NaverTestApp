package com.naver.hyeonjung.navertestapp.util;

import android.util.Log;

import static com.naver.hyeonjung.navertestapp.BuildConfig.IS_DEV;


public class LOG {

    private static final String TAG = "SearchApp";

    public static void d(String msg) {
        if (IS_DEV) Log.d(TAG, msg);
    }

    public static void e(String msg) {
         Log.e(TAG, msg);
    }

    public static void i(String msg) {
        if (IS_DEV) Log.i(TAG, msg);
    }

    public static void w(String msg) {
        if (IS_DEV) Log.w(TAG, msg);
    }

}
