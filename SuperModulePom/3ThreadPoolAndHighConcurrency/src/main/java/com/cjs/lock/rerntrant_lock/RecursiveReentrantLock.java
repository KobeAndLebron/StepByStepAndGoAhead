package com.cjs.lock.rerntrant_lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 递归锁[可重入锁]: 同一个线程在获得锁之后, 依然可以再次获得锁; synchronized和ReentrantLock都是可重入锁.
 *
 * Lock和UnLock方法必须要匹配, 如果Unlock的次数比Lock的次数少, 则其他线程永远获取不到锁.
 * 如果Unlock的次数比Lock的次数多, 将会抛出IllegalMonitorStateException.
 *
 * @see com.cjs.lock.rerntrant_lock.NonRecursiveLock 非可重入的死锁问题.
 */
public class RecursiveReentrantLock {
    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        // 可重入锁, 不会死锁.
        for (int i = 0; i < 10; i++) {
            new Thread(RecursiveReentrantLock::get, "Thread" + i).start();
        }
    }

    public static void set() {
        lock.lock();
        lock.lock();
        try {
            System.out.println(Thread.currentThread() + " invoke set...");
        } finally {
            lock.unlock();
            lock.unlock();
        }
    }

    public static void get() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread() + " invoke get...");
            set();
            Thread.sleep(1);
            System.out.println();
            System.out.println();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
