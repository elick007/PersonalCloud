package com.elick.personalcloud.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.elick.personalcloud.api.bean.FileListBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class SharedPreferenceUtils {
    private static String MY_SPF = "MY_SPF";
    //定义一个SharePreference对象
    private static SharedPreferences sharedPreferences;

    /**
     * 存储数据
     * 这里要对存储的数据进行判断在存储
     * 只能存储简单的几种数据
     * 这里使用的是自定义的ContentValue类，来进行对多个数据的处理
     */
    //创建一个内部类使用，里面有key和value这两个值
    public static class ContentValue {
        String key;
        Object value;

        //通过构造方法来传入key和value
        public ContentValue(String key, Object value) {
            this.key = key;
            this.value = value;
        }
    }

    public static void init(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(MY_SPF, Context.MODE_PRIVATE);
        }
    }

    //一次可以传入多个ContentValue对象的值
    public static void putValues(ContentValue... contentValues) {

        //获取SharePreference对象的编辑对象，才能进行数据的存储
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //数据分类和存储
        for (ContentValue contentValue : contentValues) {
            //如果是字符型类型
            if (contentValue.value instanceof String) {
                editor.putString(contentValue.key, contentValue.value.toString()).apply();
            }
            //如果是int类型
            if (contentValue.value instanceof Integer) {
                editor.putInt(contentValue.key, Integer.parseInt(contentValue.value.toString())).commit();
            }
            //如果是Long类型
            if (contentValue.value instanceof Long) {
                editor.putLong(contentValue.key, Long.parseLong(contentValue.value.toString())).commit();
            }
            //如果是布尔类型
            if (contentValue.value instanceof Boolean) {
                editor.putBoolean(contentValue.key, Boolean.parseBoolean(contentValue.value.toString())).commit();
            }

        }
    }
//获取repo
    public static String getRepo(){
        if (sharedPreferences!=null){
            return sharedPreferences.getString("repo",null);
        }else return null;
    }

    //获取数据的方法
    public static String getString(String key) {
        if (sharedPreferences != null)
            return sharedPreferences.getString(key, null);
        else return null;
    }

    public static boolean getBoolean(String key) {
        if (sharedPreferences != null)
            return sharedPreferences.getBoolean(key, false);
        else return false;
    }

    public static int getInt(String key) {
        if (sharedPreferences != null)
            return sharedPreferences.getInt(key, -1);
        else return -1;
    }

    public static long getLong(String key) {
        if (sharedPreferences != null)
            return sharedPreferences.getLong(key, -1);
        else return -1;
    }

    //清除当前文件的所有的数据
    public static void clear() {
        sharedPreferences.edit().clear().apply();
    }

    //
    public static void storeFileList(String path, List<FileListBean.FileListItem> fileList){
        if (fileList == null){
            return;
        }else {
            Gson gson=new Gson();
            String jsonStr=gson.toJson(fileList);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString(path,jsonStr);
            editor.apply();
        }
    }
    //
    public static List<FileListBean.FileListItem> getFileList(String path){
        List<FileListBean.FileListItem> list=new ArrayList<>();
        String jsonStr=sharedPreferences.getString(path,null);
        if (jsonStr==null){
            return null;
        }else {
           Gson gson=new Gson();
           list=gson.fromJson(jsonStr,new TypeToken<List<FileListBean.FileListItem>>(){}.getType());
           return list;
        }
    }
}
