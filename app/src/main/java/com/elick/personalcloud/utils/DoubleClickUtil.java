package com.elick.personalcloud.utils;

public class DoubleClickUtil {
    private static long lastTime;
    public static void setClickTime(long time){
        lastTime=time;
    }
    public static long getLastTime(){
        return lastTime;
    }
    public static boolean canClick(long time){
        if (time-lastTime<2000){
            return true;
        }else {
            lastTime=time;
            return false;
        }
    }
}
