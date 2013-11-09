package com.gmail.hzz041120.jvm.trace;

import java.lang.reflect.Method;

public class JavaClassExecuter {

    public static String executer(byte[] classByte) {
        HackSystem.clearBuffer();
        ClassModifier cm = new ClassModifier(classByte);
        byte[] modiBytes = cm.modifyUTF8Constant("java/lang/System", "com/gmail/hzz041120/jvm/trace/HackSystem");
        HotSwapClassloader loader = new HotSwapClassloader();
        Class clazz = loader.loadClass(modiBytes);
        try {
            Method method = clazz.getMethod("main", new Class[] { String[].class });
            method.invoke(null, new String[] { null });
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return HackSystem.getBufferString();
    }
}
