package com.gmail.hzz041120.jvm.trace;

public class HotSwapClassloader extends ClassLoader {

    public HotSwapClassloader() {
        super(HotSwapClassloader.class.getClassLoader());
    }

    public Class<?> loadClass(byte[] classBytes) {
        return defineClass(null, classBytes, 0, classBytes.length);
    }
}
