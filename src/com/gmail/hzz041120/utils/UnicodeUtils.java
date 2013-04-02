package com.gmail.hzz041120.utils;

/**
 * Unicode�ַ���������
 * 
 * @author zhongzhou.hanzz 2013-1-9 ����11:32:42
 */
public class UnicodeUtils {

    public static void main(String[] args) throws Exception{
        String a = "1����ÿһ��Ϊ��λ��ֻ�С��ж��Ƿ񵥸����API���÷�ʽ=true\"����Ч<br/>";
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
