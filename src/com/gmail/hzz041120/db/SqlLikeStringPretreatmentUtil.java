package com.gmail.hzz041120.db;


/**
 * SQL LIKEÓï¾ä×Ö·û´®Ô¤´¦Àí
 */
public class SqlLikeStringPretreatmentUtil {

    public static String preMysql(String str) {
        // Ìæ»»MYSQLÍ³Åä±£Áô·ûºÅ$ _
        str = str.replaceAll("\\_", "\\\\_");
        str = str.replaceAll("\\%", "\\\\%");
        return str;
    }

}
