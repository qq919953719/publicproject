package com.cuichuang.common.util;
import android.util.Log;

import com.cuichuang.common.BuildConfig;


/**
 * Created by xys on 2017/4/20 0020.
 * 日志工具类
 */

public class ULog {
    public static final boolean isDebug = BuildConfig.DEBUG;
    private static final String TAG = "ck";
    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug)
            Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (isDebug)
            Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (isDebug)
            Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (isDebug)
            Log.v(TAG, msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag, msg);
    }
    public static void w(String tag, String msg) {
        if (isDebug)
            Log.w(tag, msg);
    }
}
