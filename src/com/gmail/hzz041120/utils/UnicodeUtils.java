package com.gmail.hzz041120.utils;

/**
 * Unicode字符辅助工具
 * 
 * @author zhongzhou.hanzz 2013-1-9 上午11:32:42
 */
public class UnicodeUtils {

    public static void main(String[] args) throws Exception{
        String a = "1、以每一行为单位；只有“判断是否单个检查API调用方式=true\"才有效<br/>";
        System.out.println(printStringAsciiValue(a));
    }

    public static String printStringOxValue(String a) {
        if (a == null || a.length() == 0) return null;
        char[] charArray = a.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : charArray) {
            sb.append(c + "\t\\u" + Integer.toHexString(c) + "\n");
        }
        return sb.toString();
    }
    
    public static String printStringAsciiValue(String a) {
        if (a == null || a.length() == 0) return null;
        char[] charArray = a.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : charArray) {
            sb.append(c + "\t%" + Integer.toHexString(c) + "\n");
        }
        return sb.toString();
    } 
}
