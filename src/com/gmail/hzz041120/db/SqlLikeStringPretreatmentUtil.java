package com.gmail.hzz041120.db;

import java.util.regex.Matcher;

/**
 * SQL LIKEÓï¾ä×Ö·û´®Ô¤´¦Àí
 */
public class SqlLikeStringPretreatmentUtil {

    public static String preMysql(String str) {
     // Ìæ»»MYSQLÍ³Åä±£Áô·ûºÅ$ _
        str = Matcher.quoteReplacement(str);
        str.replaceAll("\\_", "\\\\_");
        return str;
    }
    
}
