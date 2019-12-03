package com.cjs.lock.cas;

import java.util.concurrent.atomic.AtomicInteger;

public class ABAProblemDemo {
    static AtomicInteger atomicInteger = new AtomicInteger(1000);

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
            System.out.println(Thread.currentThread() + "修改结果:" + atomicInteger.compareAndSet(1000, 1001) + "\t" + atomicInteger.get());
            System.out.println(Thread.currentThread() + "修改结果:" + atomicInteger.compareAndSet(1001, 1000) + "\t" + atomicInteger.get());
            System.out.println(Thread.currentThread() + "ABA--end");
        }, "ABA").start();

        new Thread(() -> {

            int i = atomicInteger.get();
            System.out.println(Thread.currentThread() + "线程BBB执行, 取到初始值为: " + i);
            // 阻塞, 保证上个线程完成ABA操作.
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 线程B修改成功, 没有感知到ABA线程的ABA过程.
            System.out.println(Thread.currentThread() + "修改结果:" + atomicInteger.compareAndSet(i, 2009) + "\t" + atomicInteger.get());
        }, "LowPriorityThread").start();

    }
}
