package com.elick.personalcloud.config;

public class Constans {
    public static String base_url="http://104.206.144.11:8000/";
    private static StringBuilder currentPath=new StringBuilder("/");
    public static StringBuilder getCurrentPath() {
        return currentPath;
    }

    public static void setCurrentPath(StringBuilder currentPath) {
        Constans.currentPath = currentPath;
    }
}
