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
        for (Character c : charArray) {
            StringBuilder sb1 = new StringBuilder();
            System.out.print(c + "\t");
            sb.append("\\u");
            String tc = c + "";
            byte[] bytes = tc.getBytes();
            for (byte b : bytes) {
                if (b < 0) {
                    System.out.print(((b-1) ^ (1<<32)) + "\t");
                    sb.append(Integer.toHexString(b ^ (1<<8)));
                    sb1.append(Integer.toHexString(b ^ (1<<8)));
                } else {
                    System.out.print(b + "\t");
                    sb.append(Integer.toHexString(b));
                    sb1.append(Integer.toHexString(b));
                }
            }
            System.out.println(sb1.toString());
        }
        return sb.toString();
    }
}
