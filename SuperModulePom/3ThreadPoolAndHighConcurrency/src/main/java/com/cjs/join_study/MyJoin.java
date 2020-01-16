package com.cjs.join_study;

import com.cjs.TimerThread;

/**
 * 自己实现的join方法, 在以下两个类中被测试:
 *
 * @see MyImplementataionOfJoinTest2
 * @see MyImplementationOfJoinTest1
 */
public class MyJoin {
    private static final int mills = 3000;
    private static final Thread t = new Thread(new Runnable() {
        public void run() {
            try {
                synchronized (t) {
                    Thread.sleep(mills);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    public static void join(Thread thread, long timemills) throws InterruptedException {
        synchronized (thread) {
            // 现在的时间
            long now = System.currentTimeMillis();
            // 已经等待的时间
            long waitedTime = 0;

            if (timemills < 0) {
                throw new IllegalArgumentException("time out is negative");
            }

            if (timemills == 0) {
                while (thread.isAlive()) {
                    thread.wait(0);
                }
            } else {
                while (thread.isAlive()) {
                    // 剩余的wait时间, 如果thread运行的时间小于timemills, 那么此时delay大于0.
                    long delay = timemills - waitedTime;
                    if (delay <= 0) {
                        break;
                    }
                    thread.wait(delay);
                    waitedTime = System.currentTimeMillis() - now;
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TimerThread th = new TimerThread(mills);
        th.start();

        t.start();
        MyJoin.join(t, 0);
        System.out.println("I wanted CPU!!! but bj Thread is " + (t.isAlive() ? "alive" : "dead"));
    }

}
