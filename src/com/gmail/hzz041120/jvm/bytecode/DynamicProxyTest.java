package com.gmail.hzz041120.jvm.bytecode;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxyTest {

    interface IHello {

        void sayHello();
    }

    static class Hello implements IHello {

        @Override
        public void sayHello() {
            System.out.println("Hello World!");
        }
    }

    static class DynamicProxy<T> implements InvocationHandler {

        T originalObj;

        @SuppressWarnings("unchecked")
        T bind(T originalObj) {
            this.originalObj = originalObj;
            return (T) Proxy.newProxyInstance(originalObj.getClass().getClassLoader(),
                                              originalObj.getClass().getInterfaces(), this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("welcome!");
            return method.invoke(originalObj, args);
        }

    }

    public static void main(String[] args) {
        // 保存代理类的字节码
//        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        IHello hello = new DynamicProxy<IHello>().bind(new Hello());
        hello.sayHello();
        
        
    }
}
