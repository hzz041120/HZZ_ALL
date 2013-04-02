package com.gmail.hzz041120.jvm.pkgtest;

public class NotInitialization {

    public static void main(String[] args) {
        // 非主动使用类字段
//        System.out.println(SubClass.value);

        //通过数组定义来引用类 指令集newarray
//        SuperClass[] sca = new SuperClass[10];
        
        // 使用类编译阶段就已经置入常量池中的字段不会引发类型初始化(也不进行loading)
//        System.out.println(ConstClass.HELLO_WORLD);
        
        // 引用接口并不会初始化 不直接使用的接口常量字段类型,也并不会初始化其父类
        System.out.println(ConstInterface.OTHER_CLASS);
    }
}
