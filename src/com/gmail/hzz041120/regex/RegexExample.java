package com.gmail.hzz041120.regex;

/**
 * һЩ��������
 * 
 * @author zhongzhou.hanzz 2013-1-9 ����5:19:57
 */
public class RegexExample {

    /**
     * �ϲ��ַ����Ķ��пհ���
     * 
     * @param str
     * @return
     */
    public static String replaceEnterAndNearEnterSpace(String str) {
        return str.replaceAll("(\\s)*(\r\n(\\s)*)+", "\r\n");
    }
}
