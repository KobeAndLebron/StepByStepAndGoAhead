package com.cjs.join_study;

import java.util.concurrent.TimeUnit;

/**
 *
 * 可能运行结果1(下面以主线程先运行为例(即第26行先于Thread1执行)):
 *  Thread1 begin at 1579167526160 // 主线程线运行, Thread1加入. 由于thread1.join()的时间小于 第59行的睡眠时间, 所以主线程不会运行. 直到Thread1运行完毕.
 *  MainThread state: TIMED_WAITING; Thread2 state: BLOCKED // 主线程还在TIMED_WAITING状态.
 *  MainThread state: BLOCKED; Thread2 state: BLOCKED // 主线程被系统唤醒, 但是获取不到锁, 进入BLOCKED状态.
 *  Thread1 end at 1579167526665 // Thread1结束. 系统会调用Thread1.notifyAll方法, Thread2和主线程被唤醒.
 *  Thread2 begin at 1579167526665 // Main线程和Thread2开始运行, 假设Thread2先运行.
 *  Main method end at 1579167526678 // Thread2的sleep时间(第86行)比Main线程的sleep时间(第28行)少, 所以主线程先运行. 主线程结束.
 *  Thread2 end at 1579167527365 // Thread2结束. 程序结束.
 *
 * @see Thread#join(long)的执行原理
 *
 * Created by chenjingshuai on 17-4-28.
 */
public class Join相关多线程运行题 {
    public static void main(String[] args) throws InterruptedException {
        Thread1 thread1 = new Thread1();
        Thread thread2 = new Thread2(thread1);
        thread1.setThread2(thread2);
        thread1.setMainThread(Thread.currentThread());
        thread1.start();
        thread2.start();
        thread1.join(200);
        // 比Thread2的sleep时间短.
        Thread.sleep(10);
        System.out.println("Main method end at " + System.currentTimeMillis());
    }
}


class Thread1 extends Thread {
    private Thread thread2;
    private Thread mainThread;

    @Override
    public void run() {
        synchronized (this) {
            System.out.println("Thread1 begin at " + System.currentTimeMillis());

            int i = 2;
            while (i-- > 0) {
                // 主线程可能的状态:
                //  1. TIMED_WAITING -> BLOCKED(Main线程比Thread1先运行, 理由同JoinImplementationNote的Case1-第83行).
                //  2. BLOCKED(Thread1比Main线程先运行, Main线程的第38行得不到执行, 因为join方法也要获取Thread1的锁).
                // Thread2的状态一定为: BLOCKED.
                System.out.println("MainThread state: " + mainThread.getState() + "; Thread2 state: " + thread2.getState());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread1 end at " + System.currentTimeMillis());
        }
    }

    public void setThread2(Thread thread2) {
        this.thread2 = thread2;
    }

    public void setMainThread(Thread currentThread) {
        this.mainThread = currentThread;
    }
}

class Thread2 extends Thread {
    private final Thread thread1;

    Thread2(Thread thread1) {
        this.thread1 = thread1;
    }

    @Override
    public void run() {
        synchronized (thread1) {
            System.out.println("Thread2 begin at " + System.currentTimeMillis());
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread2 end at " + System.currentTimeMillis());
        }
    }
}
