package com.cjs.reference;

import java.lang.ref.SoftReference;

/**
 * 软引用： 堆内存够的时候不回收， 不够的时候进行回收。 用于做缓存。
 * 弱引用： 只要有垃圾回收，就会被回收。用于容器 WeakHashMap和ThreadLocal。
 * 虚引用： {@link PhantomReferenceRef}
 */
public class SoftReferenceRef {
    public static void main(String[] args) {
        // 设置堆内存为20M后再跑。
        SoftReference<byte[]> m = new SoftReference<>(new byte[1024*1024*10]);
        //m = null;
        System.out.println(m.get());
        System.gc();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 堆内存够用， 不会进行回收。
        System.out.println(m.get());

        //再分配一个数组，heap将装不下，这时候系统会垃圾回收，先回收一次，如果不够，会把软引用干掉
        byte[] b = new byte[1024*1024*15];
        System.out.println(m.get());
    }
}
