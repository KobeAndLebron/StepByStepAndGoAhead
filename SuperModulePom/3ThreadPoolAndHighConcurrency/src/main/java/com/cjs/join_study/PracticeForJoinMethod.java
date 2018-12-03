package com.cjs.join_study;

/**
 * Harvest:
 *   1. 假设主线程先运行, 即主线程先抢到thread1对象的锁(join方法会获取线程对象的锁), 抢到锁之后进入thread1
 *  对象的waitting set.
 *   2. Thread1和Thread2开始抢thread1对象的锁. Thread1抢到, 先打印Thread1 begin, 然后sleep500ms,
 *  由于在sleep时thread1不释放锁, 所以主线程(join方法要继续执行也要获线程对象的锁, 具体看join方法实现)和Thread2
 *  都运行不了, sleep过后打印Thread1 end.
 *   3. Thread1执行完临界区的代码, 释放锁；接下来主线程和Thread2抢Thread1对象的锁；
 *      3.1假如主线程先抢到, 在执行完join方法剩余的临界区的代码后， 释放thread1对象的锁，
 *     接下来就变成了主线程和Thread2的并发执行, Thread2 begin... main end... Thread2 end...
 *     或者 main end... Thread2 begin... Thread2 end...
 *      3.2假如Thread2先抢到， 那么main线程只能等到thread2运行完之后才能运行，打印结果：
 *     Thread2 begin... Thread2 end... main end...
 *
 *   假设主线程先运行, Thread2先抢到锁的情况一样.
 *   Thread1或者Thread2先运行的情况不予描述.
 *
 *   @see Thread#join(long)
 *
 * Created by chenjingshuai on 17-4-28.
 */
public class PracticeForJoinMethod {
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread1();
        Thread thread2 = new Thread2(thread1);
        thread1.start();
        thread2.start();
        thread1.join(200);
        // 此段代码可以模拟 3.1的前一种情况, 去掉则模拟3.1后一种情况. (在主线程先抢到锁的前提下)
        Thread.sleep(10);
        System.out.println("Main method end at " + System.currentTimeMillis());
    }
}


class Thread1 extends Thread {
    @Override
    public void run() {
        synchronized (this) {
            System.out.println("Thread1 begin at " + System.currentTimeMillis());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread1 end at " + System.currentTimeMillis());
        }
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
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread2 end at " + System.currentTimeMillis());
        }
    }
}
