package com.cjs.lock.cas;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁: 未获取锁的线程不会立即被阻塞, 而是采取循环的方式去获取锁, 这样做的好处是增强了程序的并发性, 缺点是会消耗CPU.
 *
 * 使用CAS来实现自旋锁.
 */
public class SpinLockImpl {
    private static MySpinLock MY_SPIN_LOCK = new MySpinLock();

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                MY_SPIN_LOCK.lock();

                System.out.println(Thread.currentThread() + "---1");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "---2");
                System.out.println();

                MY_SPIN_LOCK.unLock();
            }, "Thread" + i).start();
        }

    }

    /**
     * 自己实现的自旋锁.
     */
    public static class MySpinLock {
        private static AtomicReference<Thread> reference = new AtomicReference<>();

        public void lock() {
            // 只有第一个进来的线程会跳过此循环, 后来进来的线程由于AtomicReference的值被设为了第一个进来的线程, 都会返回False,
            // 一直在循环, 即自旋. 直到reference将值置为null.
            // 如果循环的时间过长, 会导致CPU占用率过高.
            while (!reference.compareAndSet(null, Thread.currentThread())) {

            }
        }

        public void unLock() {
            // 线程执行结束, 将引用置为null.
            reference.set(null);
        }
    }
}
