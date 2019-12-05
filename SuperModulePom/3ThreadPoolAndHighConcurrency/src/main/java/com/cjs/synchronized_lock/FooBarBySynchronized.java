package com.cjs.synchronized_lock;

/**
 * leetcode:  https://leetcode.com/problems/print-foobar-alternately/
 *
 * 通过Synchronized+wait/notify来实现.
 */
class FooBarBySynchronized {
    private int n;
    private static final Object MONITOR = new Object();
    private int signal = 0;

    public FooBarBySynchronized(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            // 通过monitorenter monitorexitJVM指令来实现.
            synchronized (MONITOR) {
                while (signal % 2 != 0) {
                    // Harvest: 同Lock一样, 没有使用Synchronized直接使用wait会报IllegalMonitorStateException.
                    MONITOR.wait();
                }

                // printFoo.run() outputs "foo". Do not change or remove this line.
                printFoo.run();
                signal++;
                // 只能唤醒一个或全部线程, 不能唤醒指定结合的线程组.
                MONITOR.notifyAll();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            synchronized (MONITOR) {
                while (signal % 2 == 0) {
                    MONITOR.wait();
                }

                // printBar.run() outputs "bar". Do not change or remove this line.
                printBar.run();

                signal++;
                MONITOR.notifyAll();
            }
        }
    }
}
