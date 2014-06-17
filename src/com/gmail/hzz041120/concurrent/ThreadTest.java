package com.gmail.hzz041120.concurrent;

import java.util.Map;

public class ThreadTest {
    public static void main(String[] args) {
        A.print();
        Map<Thread, StackTraceElement[]> statcTraceMap = Thread.getAllStackTraces();
        for(Map.Entry<Thread, StackTraceElement[]> entry : statcTraceMap.entrySet()) {
            System.out.println("thread name : " + entry.getKey().getName());
            for(StackTraceElement e : entry.getValue()) {
                System.out.println(e.getMethodName() + ":" + e.getLineNumber());
            }
            System.out.println("=========================");
        }
    }
}

class A {
    public static void print() {
        System.out.println("**********************");
        StackTraceElement[] sta = Thread.currentThread().getStackTrace();
        for(StackTraceElement e : sta) {
            System.out.println(e.getMethodName() + ":" + e.getLineNumber());
        }
        System.out.println("**********************");
        B.print();
        System.out.println("call A");
    }
}

class B {
 public static void print() {
        
        System.out.println("call B");
    }
}
