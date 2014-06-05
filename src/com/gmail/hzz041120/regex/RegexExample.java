package com.gmail.hzz041120.regex;

/**
 * 一些正则栗子
 * 
 * @author zhongzhou.hanzz 2013-1-9 下午5:19:57
 */
public class RegexExample {

    /**
     * 合并字符串的多行空白行
     * 
     * @param str
     * @return
     */
    public static String replaceEnterAndNearEnterSpace(String str) {
//        return str.replaceAll("(\\s)*(\r\n(\\s)*)+", "\r\n");
        
        
        return null;
    }
    public static void main(String[] args) {
        String match = "172\\.(1[6-9]|[2-9]\\d|\\d{3})\\.(.)*";
        String a ="202.101.23.14";
        String b ="172.1.21.111";
        String c ="172.18.1.231";
        System.out.println(a.matches(match));
        System.out.println(b.matches(match));
        System.out.println(c.matches(match));
    }
}
