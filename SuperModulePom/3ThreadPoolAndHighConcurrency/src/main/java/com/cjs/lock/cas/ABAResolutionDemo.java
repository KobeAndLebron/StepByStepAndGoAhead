package com.cjs.lock.cas;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 在比较Reference的基础上, 同时还会比较Stamp[类似版本号], 两者都相同才代表值为更新.
 * 内部通过Pair类将Reference和Stamp封装.
 *
 * -------
 *
 * 补充: 将100改为1000结果就会不对, 因为Integer.valueOf(1000) != Integer.valueOf(1000);
 * Integer初始化会有IntegerCache: {@link Integer#IntegerCache}, 默认会缓存-127到128的Integer对象, valueOf方法会先从缓存取, 没有则新建.
 */
public class ABAResolutionDemo {
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(127, 1);

    public static void main(String[] args) {
        new Thread(() -> {
            // 保证下一个线程先执行.
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // ABA
            System.out.println(Thread.currentThread() + "ABA--start");
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread() + "初始化版本号:" + stamp);

            atomicStampedReference.compareAndSet(127, 101, 1, atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread() + "第一次修改版本号: " + atomicStampedReference.getStamp() + ";" +
                " 值改为:" + atomicStampedReference.getReference());
            atomicStampedReference.compareAndSet(101, 127, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);

            System.out.println(Thread.currentThread() + "第二次修改版本号: " + atomicStampedReference.getStamp() + "; " +
                "值恢复为:" + atomicStampedReference.getReference());
            System.out.println(Thread.currentThread() + "ABA--end");
        }, "ABA").start();

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread() + "初始化版本号:" + stamp + "; 初始化值: " + atomicStampedReference.getReference());

            System.out.println(Thread.currentThread() + "线程BBB执行");
            // 阻塞, 保证上个线程完成ABA问题.
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 当前线程Reference的版本号已经和刚开始不一样, 所以更新失败.
            System.out.println(Thread.currentThread() +
                "修改结果:" + atomicStampedReference.compareAndSet(127, 2009, stamp, atomicStampedReference.getStamp()) +
                "\t 最新版本号" + atomicStampedReference.getStamp());
            System.out.println(Thread.currentThread() + "当前最新值:" + atomicStampedReference.getReference());
        }, "LowPriorityThread").start();

    }
}
