package com.cjs.example;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 根据AQS实现{@link java.util.concurrent.CountDownLatch}.
 *
 * CountDownLatch适用场景:
 *  1. 开发一个并发测试工具时，主线程需要等到所有测试线程均执行完成再开始统计总共耗费的时间.
 *  2. 并行处理一个文件夹下的所有文件, 当所有文件的数据处理完毕时, 再进行其他操作(divide a problem into N parts).
 */
public class MyCountDownLatch extends AbstractQueuedSynchronizer {

    public MyCountDownLatch(int state) {
        super();
        setState(state);
    }

    /**
     * JDK使用的是tryAcquireShared, 原因在于可以在getCount为0时, 可以唤醒多个线程(位于CLH队列的所有线程), 而tryAcquire是排他模式, 只能唤醒一个线程.
     *
     * @param arg
     * @return
     */
    @Override
    protected boolean tryAcquire(int arg) {
        return getState() == 0;
    }

    @Override
    protected boolean tryRelease(int arg) {
        // 自己写的代码, 相当于一个do while循环.
//        int state = getState();
//        if (state > 0) {
//            while (!compareAndSetState(state, state - 1)) {
//                state = getState();
//                if (state == 0) {
//                    return true;
//                }
//            }
//        }
//        return getState() == 0;

        // JDK源代码. CAS+自旋.
        do {
            int c = getState();
            if (c == 0)
                return false;
            int nextc = c - 1;
            if (compareAndSetState(c, nextc))
                return nextc == 0;

        } while (true);
    }

    public void await() {
        super.acquire(1);
    }

    public void countDown() {
        super.release(1);
    }

    public int getCount() {
        return getState();
    }

    public static void main(String[] args) {
        MyCountDownLatch myCountDownLatch = new MyCountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread());

                System.out.println("CountDown: " + myCountDownLatch.getCount());
                myCountDownLatch.countDown();
            }).start();
        }

        myCountDownLatch.await();

        System.out.println("End---");
    }
}
