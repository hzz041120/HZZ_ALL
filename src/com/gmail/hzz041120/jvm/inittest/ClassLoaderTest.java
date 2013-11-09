package com.gmail.hzz041120.jvm.inittest;

import java.io.IOException;
import java.io.InputStream;

/**
 * ClassLoader and instanceof
 * 
 * @author zhongzhou.hanzz 2013-4-7 ÏÂÎç11:33:21
 */
public class ClassLoaderTest {

    public static void main(String[] args) throws Exception {
        ClassLoader myLoader = new ClassLoader() {

            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                InputStream is = getClass().getResourceAsStream(fileName);
                if (is == null) {
                    return super.loadClass(name);
                }
                try {
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };
        Object obj = myLoader.loadClass("com.gmail.hzz041120.jvm.inittest.ClassLoaderTest").newInstance();
        System.out.println(obj.getClass());
        System.out.println(obj instanceof ClassLoaderTest);
        System.out.println(ClassLoaderTest.class.isAssignableFrom(obj.getClass()));
        System.out.println(ClassLoaderTest.class.isInstance(obj));

        Object equalsObj = new ClassLoaderTest();
        // Throw ClassCastException
        // System.out.println(equalsObj.getClass().equals(((ClassLoaderTest)obj).getClass()));

        System.out.println(equalsObj instanceof ClassLoaderTest);
        System.out.println(ClassLoaderTest.class.isAssignableFrom(equalsObj.getClass()));
        System.out.println(ClassLoaderTest.class.isInstance(equalsObj));
    }

}
