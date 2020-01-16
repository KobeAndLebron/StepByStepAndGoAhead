package com.cjs.lock.rerntrant_lock;

import com.cjs.lock.cas.SpinLockImpl;

/**
 *  非可重入锁, 一个线程多次获取锁会死锁.
 */
public class NonRecursiveLock {
    // 此处用自旋锁表示非可重入锁.
    private static SpinLockImpl.MySpinLock spinLock = new SpinLockImpl.MySpinLock();

    public static void main(String[] args) {
        // 非可重入锁, doSomething和doOtherThing必须同步进行.
        doSomething();
    }

    public static void doSomething() {
        spinLock.lock();
        System.out.println("doSomething");
        doOtherThing();
        spinLock.unLock();
    }

    public static void doOtherThing() {
        /**
         * Harvest: 因为锁是不可重入的, 所以相当于它自己还没释放锁, 但是它想获取锁, 产生了死锁现象.
         */
        spinLock.lock();
        System.out.println("doOtherThing");
        spinLock.unLock();
    }
}
