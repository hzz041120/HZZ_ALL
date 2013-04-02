package com.gmail.hzz041120.jvm.pkgtest;

public class ConstClass {

    static {
        System.out.println("ConstClass init!");
    }

    public static final String HELLO_WORLD = "hello world";
}
