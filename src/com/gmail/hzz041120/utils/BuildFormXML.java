//package com.gmail.hzz041120.utils;
//
//import java.lang.reflect.Field;
//
//import com.alibaba.intl.sourcing.shared.margin.open.share.dto.log.HdMarginTradingLience;
//import com.alibaba.intl.sourcing.shared.margin.open.share.dto.log.util.FormField;
//
//public class BuildFormXML {
//
//    public static void main(String[] args) {
//        Class<?> targetClass = HdMarginTradingLience.class;
//        Field[] fields = targetClass.getDeclaredFields();
//        for (Field field : fields) {
//            String fieldName = field.getName();
//            if (field.getAnnotation(FormField.class) == null) {
////                System.err.println(field.getName());
//                continue;
//            }
//            String disName = field.getAnnotation(FormField.class).name();
//            String validateName = "required-validator";
//            System.out.println(buildFieldFormString(fieldName, disName, validateName));
//        }
//    }
//
//    private static String buildFieldFormString(String fieldName, String disName, String validateName) {
//        return "<field name=\"" + fieldName + "\" displayName=\"" + disName + "\">\r\n\t<" + validateName
//               + ">\r\n\t\t<message>${displayName}²»ÄÜÎª¿Õ£¡</message>\r\n\t</" + validateName + ">\r\n</field>";
//    }
//}
