package com.gmail.hzz041120.jvm.inittest;

public class TestStaticAsignmentInit {

    static {
        //不能使用，但能赋值
//        System.out.println(A);
        A = 3;
    }

    static int A = 2;

    public static void main(String[] args) {
        System.out.println(A);
    }
}
