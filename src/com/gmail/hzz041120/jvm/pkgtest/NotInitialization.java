package com.gmail.hzz041120.jvm.pkgtest;

public class NotInitialization {

    public static void main(String[] args) {
        // ������ʹ�����ֶ�
//        System.out.println(SubClass.value);

        //ͨ�����鶨���������� ָ�newarray
//        SuperClass[] sca = new SuperClass[10];
        
        // ʹ�������׶ξ��Ѿ����볣�����е��ֶβ����������ͳ�ʼ��(Ҳ������loading)
//        System.out.println(ConstClass.HELLO_WORLD);
        
        // ���ýӿڲ������ʼ�� ��ֱ��ʹ�õĽӿڳ����ֶ�����,Ҳ�������ʼ���丸��
        System.out.println(ConstInterface.OTHER_CLASS);
    }
}
