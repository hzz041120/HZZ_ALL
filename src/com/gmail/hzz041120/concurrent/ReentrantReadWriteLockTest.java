package com.gmail.hzz041120.concurrent;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockTest {
    final static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    
    public static void main(String[] args) throws Exception{
        Runnable runnable = new Runnable(){
            public void run() {
                lock.readLock().lock();
//                throw new InterruptedException();
            }};
        new Thread(runnable).start();
        Thread.sleep(500);
        System.out.println(lock.readLock().toString());
    }
}
