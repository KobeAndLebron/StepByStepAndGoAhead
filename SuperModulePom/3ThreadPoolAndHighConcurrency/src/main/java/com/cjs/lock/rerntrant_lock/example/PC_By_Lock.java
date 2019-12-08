package com.cjs.lock.rerntrant_lock.example;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者消费者模式, 传统版: 使用Lock+await/signalAll来实现.
 */
public class PC_By_Lock {
    public static void main(String[] args) {
        ShareData shareData = new ShareData();

        for (int j = 0; j < 5; j++) { // 5个生产者.
            new Thread(() -> {
                for (int i = 0; i < 5; i++) {
                    shareData.increment();
                }
            }, "Producer" + j).start();
        }

        for (int j = 0; j < 5; j++) { // 5个消费者.
            new Thread(() -> {
                for (int i = 0; i < 5; i++) {
                    shareData.decrement();
                }
            }, "Consumer" + j).start();
        }
    }


    static class ShareData {
        // 信号量. 0-9表示可生产. 1-9表示可消费.
        // 多个线程同时操作i, 需要保证原子性.
        private volatile AtomicInteger atomicInteger = new AtomicInteger(0);

        private Lock lock = new ReentrantLock();
        private Condition condition = lock.newCondition();

        public void increment() {
            try {
                lock.lock();

                while (atomicInteger.get() >= 10) { // 等待, 不能生产.
                    condition.await();
                }

                System.out.println(Thread.currentThread() + ": " + atomicInteger.incrementAndGet());

                condition.signalAll();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void decrement() {
            try {
                lock.lock();

                while (atomicInteger.get() == 0) { // 等待, 不能消费.
                    condition.await();
                }

                System.out.println(Thread.currentThread() + " : " + atomicInteger.getAndDecrement());

                condition.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
