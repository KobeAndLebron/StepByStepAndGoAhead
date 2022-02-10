package com.cjs.example;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 多个线程之间互相等待, 直到达到同一个同步点, 先执行BarrierAction, 再继续一起执行.
 *
 * Cyclic: 所有等待线程被释放后, 可以被复用.
 * Barrier: 所有线程都会被栅栏拦住, 当都到达时, 一起跳过栅栏执行.
 * A synchronization aid that allows a set of threads to all wait for each other to reach a common barrier point.
 *
 * 原理:
 * 1. 调用{@linkplain CyclicBarrier#await()}方法的时候, 先将count--(count是尚未调用await方法的数量), 然后判断count是否为0;
 * 如果Count大于0, 则证明还有线程未调用await方法, 此时调用Lock.newCondition.await(), 将当前线程放入Condition队列.
 * 如果Count等于0, 则证明所有的线程均已调用await方法, 此时调用barrierAction, barrierAction执行完毕后再Lock.unlock,
 * 处于Condition队列的线程都将得到执行. 将Barrier复原, count=parties.
 *
 * -------
 * CountdownLatch和CyclicBarrier的区别:
 *  1. 实现方式不同, 前者直接使用AQS, 后者使用ReentrantLock+Condition.
 *  2. 前者不可复用, 后者可复用.
 *  3. 调用CountDownLatch的countDown方法后，当前线程并不会阻塞，会继续往下执行；
 *  而调用CyclicBarrier的await方法，会阻塞当前线程，直到CyclicBarrier指定的线程全部都到达了指定点的时候，才能继续往下执行；
 */
public class CyclicBarrierExample {
    public static void main(String[] args) {
        final int N = 10;
        Thread[] allSoldiers = new Thread[N];

        boolean flag = false;
        CyclicBarrier cyclic = new CyclicBarrier(N, new BarrierAction(flag, N));

        System.out.println("集合队伍! ");
        for (int i = 0; i < N; i++) {
            allSoldiers[i] = new Thread(new Solider("士兵" + i, cyclic));
            allSoldiers[i].start();
        }


    }

    static class Solider implements Runnable {
        CyclicBarrier cyclicBarrier;
        String name;

        public Solider(String name, CyclicBarrier cyclicBarrier) {
            this.name = name;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                // numberWaiting为调用await()方法被阻塞的线程.
                System.out.println(name + "报道! (大约)还有" + (cyclicBarrier.getParties() - cyclicBarrier.getNumberWaiting()) + "个未报道.");
                cyclicBarrier.await();
                doWork();
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        void doWork() {
            try {
                // 为了每个线程休眠时间不一致【体现互相等待】
                Thread.sleep(Math.abs(new Random().nextInt() % 10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + ":任务完成");
        }
    }

    static class BarrierAction implements Runnable {
        boolean isFlag;
        int num;

        public BarrierAction(boolean isFlag, int num) {
            this.isFlag = isFlag;
            this.num = num;
        }

        @Override
        public void run() {
            if (isFlag) {
                System.out.println("司令:[士兵" + num + "个,任务完成!]");
            } else {
                System.out.println("司令:[士兵" + num + "个,集合完毕!]");
                isFlag = true;
            }
        }
    }

}
