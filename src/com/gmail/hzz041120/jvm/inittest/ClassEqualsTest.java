package com.gmail.hzz041120.jvm.inittest;

import com.gmail.hzz041120.jvm.pkgtest.ConstInterface;
import com.gmail.hzz041120.jvm.pkgtest.SubClass;
import com.gmail.hzz041120.jvm.pkgtest.SuperClass;

/**
 * clazz.isAssignalbeFrom(o.clz) == clazz.instanceof(o)
 * 
 * @author zhongzhou.hanzz 2013-4-7 ÏÂÎç11:10:58
 */
public class ClassEqualsTest {

    public static void main(String[] args) {
        // true result
        System.out.println(SubClass.class.isAssignableFrom(SubClass.class));
        System.out.println(SuperClass.class.isAssignableFrom(SubClass.class));
        System.out.println(ConstInterface.class.isAssignableFrom(SubClass.class));

        // true
        SubClass subclass = new SubClass();
        System.out.println(SubClass.class.isInstance(subclass));
        System.out.println(SuperClass.class.isInstance(subclass));
        System.out.println(ConstInterface.class.isInstance(subclass));

        // true
        System.out.println(subclass instanceof SubClass);
        System.out.println(subclass instanceof SuperClass);
        System.out.println(subclass instanceof ConstInterface);
    }
}
