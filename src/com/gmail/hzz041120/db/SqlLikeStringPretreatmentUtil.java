package com.gmail.hzz041120.db;

import java.util.regex.Matcher;

/**
 * SQL LIKE����ַ���Ԥ����
 */
public class SqlLikeStringPretreatmentUtil {

    public static String preMysql(String str) {
     // �滻MYSQLͳ�䱣������$ _
        str = Matcher.quoteReplacement(str);
        str.replaceAll("\\_", "\\\\_");
        return str;
    }
    
}
