package com.gmail.hzz041120.collections;

import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * �ѣ�Heap��Ҳ�����ȼ����У�PriorityQueue��
 * 
 * @author zhongzhou.hanzz 2013��11��1�� ����11:22:09
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
        // ������
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
        System.out.println("============");
        pq.remove();
        iter = pq.iterator();
        // ������
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }
}
