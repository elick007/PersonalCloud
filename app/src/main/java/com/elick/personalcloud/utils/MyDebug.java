package com.elick.personalcloud.utils;

import android.util.Log;

public class MyDebug {
    private static boolean isDebug;
    private static String TAG="eliDebug";
    public static void d(String msg){
        if (isDebug){
            Log.d(TAG,msg);
        }
    }
    public static void e(String msg){
        if (isDebug){
            Log.e(TAG,msg);
        }
    }
    public static void setIsDebug(boolean b){
        isDebug=b;
    }
}
