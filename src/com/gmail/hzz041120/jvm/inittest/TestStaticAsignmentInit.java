package com.gmail.hzz041120.jvm.inittest;

public class TestStaticAsignmentInit {

    static {
        //����ʹ�ã����ܸ�ֵ
//        System.out.println(A);
        A = 3;
    }

    static int A = 2;

    public static void main(String[] args) {
        System.out.println(A);
    }
}
