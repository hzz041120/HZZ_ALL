package com.gmail.hzz041120.collections;

import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * 堆（Heap）也叫优先级队列（PriorityQueue）
 * 
 * @author zhongzhou.hanzz 2013年11月1日 下午11:22:09
 */
public class PriorityQueueTest {

    public static void main(String[] args) {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
        pq.add(10);
        pq.add(15);
        pq.add(25);
        pq.add(30);
        pq.add(56);
        pq.add(70);
        Iterator<Integer> iter = pq.iterator();
        // 并不是
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
        System.out.println("============");
        pq.remove();
        iter = pq.iterator();
        // 并不是
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }
}
