package com.cjs.synchronized_lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * leetcode:  https://leetcode.com/problems/print-foobar-alternately/
 *
 * 通过Lock+Condition来实现.
 * 这种方法执行效率更高.
 */
class FooBarByLock {
    private int n;
    // 默认为非公平锁.
    private ReentrantLock reentrantLock = new ReentrantLock();
    /**
     * 通过Condition可以精确唤醒线程组, 更精确的控制线程的执行顺序.
     * 精确控制线程组的执行顺序案例:{@link com.cjs.lock.rerntrant_lock.example.PrintAlternately}
     */
    private Condition condition = reentrantLock.newCondition();
    int signal = 0;

    public FooBarByLock(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            reentrantLock.lock();

            while (signal % 2 != 0) {
                // Harvest: 同Synchronized一样, 没有使用lock直接使用await会报IllegalMonitorStateException.
                condition.await();
            }

            // printFoo.run() outputs "foo". Do not change or remove this line.
            printFoo.run();
            signal++;
            condition.signalAll();
            reentrantLock.unlock();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            reentrantLock.lock();

            while (signal % 2 == 0) {
                condition.await();
            }

            // printBar.run() outputs "bar". Do not change or remove this line.
            printBar.run();

            signal++;
            condition.signalAll();
            reentrantLock.unlock();
        }
    }
}
