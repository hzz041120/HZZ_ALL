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
        return str.replaceAll("(\\s)*(\r\n(\\s)*)+", "\r\n");
    }
}
