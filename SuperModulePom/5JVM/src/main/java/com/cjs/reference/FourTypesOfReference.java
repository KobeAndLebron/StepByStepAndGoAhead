package com.cjs.reference;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * 引用类型的目的:
 *  Java中垃圾回收器的运行时机是JVM操作的, 通过引用类型可以在一定程度上与垃圾回收器进行交互.
 *  JMV可以根据引用类型来判断垃圾回收器对它的回收时机, 这样可以更好地帮助垃圾回收器管理好应用的内存.
 *
 * JVM中的四大引用类型(位于java.lang.ref包下):
 *  1. 强引用, 只要引用存在, 垃圾回收器就不会对其进行回收; 即使JVM报OOM异常使程序终止运行, 也不会回收具有强引用的对象来解决内存不足的问题.
 *    Object obj = new Object();
 *  2. 软引用, 可有可无的引用, 垃圾回收器只有在内存不足的时候才会对其回收, 内存充足不会对其回收.
 *    SoftReference<Object> obj = new SoftReference<>(new Object());
 *  3. 弱引用, 可用可无的引用, 只要在GC回收的区域, 就会被回收.
 *    WeakReference<Object> obj = new WeakReference<>(new Object());
 *  4. 虚引用, 暂时不了解.
 *
 * 作用:
 *  1. 软引用可用于构建高速缓存; 比如一个应用要大量缓存图片, 可以使用Map<String, SoftReference<BitMap>> = new HashMap<>(); key用来存图片名, value用来存图片的二进制字符串.
 * 当因为图片过多而内存不足的时候, GC会将软引用所指向的图片对象给回收掉. 进而释放内存避免OOM. Mybatis源码大量使用软引用.
 */
public class FourTypesOfReference {

    // VM参数 -Xmx10M -Xms10M -Xmn4M -XX:+PrintGCDetails
    public static void main(String[] args) {
        softReference();
        // weakReference();
    }

    public static void softReference() {
        SoftReference<Object> softReference = new SoftReference<>(new Object());

        try {
            // MinorGC+FullGC.
            System.gc();
        } finally {
            System.out.println("内存足够, SoftReference不会被回收: " + softReference.get());
        }

        try {
            byte[] bytes = new byte[10 * 1024 * 1024];
            // 发生OOM异常, 内存不够, 将回收SoftReference的对象.
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            System.out.println("内存不够, SoftReference将会被回收:" + softReference.get());
        }
    }

    public static void weakReference() {
        WeakReference<Object> weakReference = new WeakReference<>(new Object());
        System.out.println("1");
        // Harvest: Eden区放不下5M的字节数组, 触发内存担保机制直接把新对象放入到OldGen.
        byte[] bytes = new byte[5 * 1024 * 1024];
        System.out.println("2");

        System.out.println("未发生GC, 弱引用对象: " + weakReference.get());
        // MinorGC, 且内存足够, 但还是会回收weakReference.
        byte[] bytes1 = new byte[2 * 1024 * 1024];
        System.out.println("3");
        System.out.println("不管内存够不够, GC都会将弱引用回收: " + weakReference.get());
    }
}
