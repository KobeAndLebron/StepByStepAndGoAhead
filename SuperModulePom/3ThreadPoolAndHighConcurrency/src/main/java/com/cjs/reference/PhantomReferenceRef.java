package com.cjs.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.LinkedList;
import java.util.List;

/**
 * 虚引用：用于虚引用被回收时，会通知一个队列。用于做堆外内存回收(Netty). 在DirectByteBuffer
 *
 * TODO 按照代码敲一遍, 还不理解。
 */
public class PhantomReferenceRef {

    private static final List<Object> LIST = new LinkedList<>();
    private static final ReferenceQueue<String> QUEUE = new ReferenceQueue<>();



    public static void main(String[] args) {
        PhantomReference<String> phantomReference = new PhantomReference<>(new String(), QUEUE);


        new Thread(() -> {
            while (true) {
                LIST.add(new byte[1024 * 1024]);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                System.out.println(phantomReference.get());
            }
        }).start();

        new Thread(() -> {
            while (true) {
                Reference<? extends String> poll = QUEUE.poll();
                if (poll != null) {
                    System.out.println("--- 虚引用对象被jvm回收了 ---- " + poll);
                }
            }
        }).start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
