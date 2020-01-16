package com.cjs.synchronized_lock;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Lock方法通过改变AQS中state属性{@linkplain AbstractQueuedSynchronizer#state}的值来上锁.
 * Synchronized是通过改变对象的对象头.
 *
 * ----------
 * JAVA中对象的布局:
 *  1. JAVA对象的实例数据.
 *  2. 对象头: MarkWord(标记字段) KlassPointer(类型指针).
 *      2.1 MarkWord在64位架构下是8个字节, 在32位架构下是4个字节, 都为1个work(字宽).
 *      2.2 被用于 1. Biased Lock, 通过它, 虚拟机可以实现有效率的Synchronized.
 *                2. 存储对象的年龄.
 *                3. 存储HashCode.
 *  3. 对齐数据: 对象大小必须是8 byte的倍数[64位虚拟机], 用于对齐. 如果前两部分加起来是是16个字节, 则不需要对齐数据. TODO 为什么是8的倍数.
 *
 *  ----------
 *  TODO: https://wiki.openjdk.java.net/display/HotSpot/Synchronization
 */
public class SynchronizedTheory {
    static class N {
        private int i; // 4 bytes.
        private boolean aBoolean; // 1字节
    }

    // 整个对象在堆中占: 对象头(12字节: MarkWord占8字节, 一个字宽(64位虚拟机). KlassPointer占32个比特位-因为开启了指针压缩)
    // + 实例数据(5字节) + 对齐数据(7字节) = 24 bytes
    private static final N n = new N();

    public static void main(String[] args) {
        System.out.println("无锁 + 调用完HashCode方法前: " + ClassLayout.parseInstance(n).toPrintable());

        // 调用HashCode方法后, 会将HashCode的值存在MarkWord中.
        System.out.println("HashCode值:" + Integer.toHexString(n.hashCode()));
        System.out.println("无锁 + 调用完HashCode方法后: " + ClassLayout.parseInstance(n).toPrintable());

        new Thread(() -> {
            // Synchronized之前, 处于无锁状态.
            // 此时MarkWord的布局为(64位):
            // unused:25, identity_hashcode:31, unused:1, age:4, biased_lock:1(0), lock:2(01).
            synchronized (n) {
                // Synchronized之后只有一个线程获得锁, 不存在多线程竞争的情况, 此时处于偏向锁状态.

                // 此时MarkWord的布局为(存储的是持有偏向锁ID):
                // ThreadId:54, epoch:2, unused:1, age:4, biased_lock:1(1), lock:2(01). TODO 待验证.

                // 偏向锁优点: 在不存在多线程竞争的情况下, 尽量减少不必要的轻量级锁执行路径, 因为轻量级锁的
                // 获取及释放依赖多次CAS原子指令, 而偏向锁只需要在置换ThreadId的时候依赖一次CAS即可.

                // 偏向锁使用场景: 如果一个线程获得了锁，那么锁就进入偏向模式。当这个线程再次请求锁时，无须再做任何同步操作。
                // 这样就节省了大量有关锁申请的操作，从而提高了程序性能。因此，对于几乎没有锁竞争的场合，偏向锁有比较好的优化效果，
                // 因为连续多次极有可能是同一个线程请求相同的锁。而对于锁竞争比较激烈的场合，其效果不佳。
                // 因为在竞争激烈的场合，最有可能的情况是每次都是不同的线程来请求相同的锁。这样偏向模式会失效，因此还不如不启用偏向锁
                System.out.println("BiasedLock: " + ClassLayout.parseInstance(n).toPrintable());
                try {
                    Thread.sleep(200000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }// 0x0000700001f68000
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (n) {
                // 当锁的状态是轻量级锁的时候, 此时如果有另外的线程来竞争, 偏向锁就会升级为轻量级锁, 其他线程会通过自旋的形式
                // 尝试获取锁, 不会阻塞(省去上下文切换的开销), 从而提高效率.

                // 轻量级锁的优点: 在锁的持有时间较短的时候, 竞争的线程不会阻塞, 提高了程序的响应速度.
                // 此时MarkWord的布局:
                // ptr_to_lock_word:62, lock(00)

                // ---------------


                // 当自旋超过一定次数或者一个线程持有锁, 一个在自旋, 又有第三者来竞争, 这时候轻量级锁升级为重量级锁. 此时等待锁的线程
                // 都会进入阻塞状态.

                // 重量级锁优点: 在锁的持有时间过长或锁竞争激烈的时候, 避免因轻量级锁自旋而过度浪费CPU的问题.

                // 此时Mark Word的布局:
                // ptr_to_heavyweight_monitor:62, lock:2(10)


            }
        }).start();

        while (true) {
            try {
                // 第一次为轻量级锁.
                // 自旋超过一定次数后, 升级为重量级锁.
                // 第二次为重量级锁.
                System.out.println("After BiasedLock(LightLock or WeightLock): " + ClassLayout.parseInstance(n).toPrintable());
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
