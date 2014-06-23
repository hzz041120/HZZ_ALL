package com.gmail.hzz041120.btrace.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorTest {

    public static void main(String[] args) throws Exception{
        Thread.sleep(15000);
        ExecutorService es = Executors.newFixedThreadPool(3);
        Thread.sleep(60000);
        ExecutorService es1 = Executors.newFixedThreadPool(3);
    }
}
