package com.gmail.hzz041120.db;


/**
 * SQL LIKE����ַ���Ԥ����
 */
public class SqlLikeStringPretreatmentUtil {

    public static String preMysql(String str) {
        // �滻MYSQLͳ�䱣������$ _
        str = str.replaceAll("\\_", "\\\\_");
        str = str.replaceAll("\\%", "\\\\%");
        return str;
    }

}
