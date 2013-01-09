package com.gmail.hzz041120.utils;

public class UnicodeUtils {

    public static void main(String[] args) {
        String a = "##链接从ma account setting 的portal页点击时，需要宽屏显示（没有左侧菜单）。";
        System.out.println(printStringOxValue(a));
    }

    private static String printStringOxValue(String a) {
        if (a == null || a.length() == 0) return null;
        char[] charArray = a.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : charArray) {
            sb.append(c + "\t\\u" + Integer.toHexString(c) + "\n");
        }
        return sb.toString();
    }
}
