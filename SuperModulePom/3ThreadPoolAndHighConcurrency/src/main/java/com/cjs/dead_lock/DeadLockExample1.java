package com.cjs.dead_lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 死锁产生原因:
 *   两个或两个以上的进程在执行过程中, 由于竞争资源或彼此通信而造成的一种阻塞现象, 若无外力作用, 他们将都无法推进下去. 此时的现象就为死锁现象.
 *
 *   如果线程A持有锁L并且想获得锁M，线程B持有锁M并且想获得锁L，那么这两个线程将永远等待下去，这种情况就是最简单的死锁形式; 这个类描述的也是这个例子.
 *
 *
 * -------
 * 死锁问题排查:
 *  1. 首先使用jps查看当前死锁的JVM进程ID.
 *  2. 然后使用jstack命令来查看线程栈信息, 会出现以下信息(双方线程都在获取被对方持有的资源, 因为资源不会被释放, 所以双方会一直等待下去...):
 *      Found one Java-level deadlock:
 * =============================
 * "线程2":
 *   waiting for ownable synchronizer 0x000000076ac30998, (a java.util.concurrent.locks.ReentrantLock$NonfairSync),
 *   which is held by "线程1"
 * "线程1":
 *   waiting for ownable synchronizer 0x000000076ac309c8, (a java.util.concurrent.locks.ReentrantLock$NonfairSync),
 *   which is held by "线程2"
 *
 *
 * -------
 *  如何避免死锁?
 *      1. 以确定的顺序获得锁, 尽量减少嵌套的加锁数量.
 *      2. 既然死锁的产生是两个线程无线等待对方持有的锁, 那么只要等待时间有个上限就可以解决这个问题了. synchronized不能做到这一点,
 *      但是{@linkplain Lock#tryLock(long, TimeUnit)}可以指定一个超时时限, 在等待超过该时限后返回一个失败信息.
 *
 *      在数据库系统的设计中考虑了监测死锁以及从死锁中恢复，数据库如果监测到了一组事务发生了死锁时，将选择一个牺牲者并放弃这个事务。
 *
 *
 * -------
 * 补充(Harvest):
 *
 *      1. JVM线程和Linux操作系统的本地线程一一对应, jstack里面的信息: nid=0x219c, 表示的就是本地(native)的线程id, 16进制转化为10进制之后的值:
 *  2*16^3 + 1*16^2 + 9*16 + 12 = 8604, 就是本地线程的id.
 *      2. 通过命令`top -Hp ${JPS_ID}`可以查看这个JAVA进程中所有线程的CPU占用情况, 将PID(10进制)转化为nid(16进制)就是JAVA进程里的本地
 *  线程id. 通过这个过程也能定位到导致CPU占用过高的代码位置.
 */
public class DeadLockExample1 {
    private static Lock lock1 = new ReentrantLock();
    private static Lock lock2 = new ReentrantLock();

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                lock1.lock();
                System.out.println(Thread.currentThread().getName() + "获得锁1.");
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + "获取锁2中....");
                lock2.lock();
                System.out.println(Thread.currentThread().getName() + "获得锁2, 正常结束.");
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }, "线程1").start();

        new Thread(() -> {
            boolean isLock1 = false;
            try {
                lock2.lock();
                System.out.println(Thread.currentThread().getName() + "获得锁2.");
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + "获取锁1中....");
                // 死锁.
                lock1.lock();

                // 解决死锁的代码.
                /*if (!lock1.tryLock(10, TimeUnit.SECONDS)) {
                    System.out.println(Thread.currentThread().getName() + " 10s内还未获取到锁1, 结束, 返回失败信息. ");
                } else {
                    System.out.println(Thread.currentThread().getName() + " 10s内获取到锁1, 结束. ");
                    isLock1 = true;
                }*/
            } finally {
                System.out.println("超时释放");
                lock2.unlock();
                if (isLock1) {
                    lock1.unlock();
                }
            }
        }, "线程2").start();
    }
}
