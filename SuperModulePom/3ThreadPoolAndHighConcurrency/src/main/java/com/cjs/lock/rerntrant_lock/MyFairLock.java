package com.cjs.lock.rerntrant_lock;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.LockSupport;

/**
 * 自己实现的ReentrantLock的公平+可重入版本锁.
 *
 * 主要实现机制为AQS同步器, AQS的实现原理: CAS + 自旋 + LockSupport + CLH.
 * 这里因为只有一个锁机制, 所以未将AQS抽象出来.
 *
 * 待实现:
 * 1. 非公平锁.
 * 2. Condition条件下的await和notify方法.
 */
public class MyFairLock {
    // 锁的状态, 0表示可获取状态, 大于0表示当前线程获取锁的次数[可重入锁].
    private volatile int state;
    // 当前锁的线程持有者.
    private Thread exclusiveOwnerThread;
    // 等待锁的线程队列. FIFO.
    private ConcurrentLinkedQueue<Thread> waiterThreadQueue = new ConcurrentLinkedQueue<>();

    private static Unsafe unsafe;
    // 用于Unsafe的CAS操作.
    private long stateValueOffset;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            // 变量私有时, 需要采取此操作.
            theUnsafe.setAccessible(true);

            /**
             *  Harvest: 由于Unsafe为引导类加载器加载, {@link Unsafe#getUnsafe())方法里面判断如果调用的
             * 类不是由系统类加载器所加载, 就会报错, 所以只能通过反射的方式来获取Unsafe对象.
             */
            unsafe = (Unsafe) theUnsafe.get(null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new Error(e);
        }
    }

    public MyFairLock() {
        try {
            stateValueOffset = unsafe.objectFieldOffset(MyFairLock.class.getDeclaredField("state"));
        } catch (NoSuchFieldException e) {
            throw new Error(e);
        }
    }

    public void lock() {
        Thread currentThread = Thread.currentThread();
        if (state == 0) {
            // 获取state, 成功则得到锁, 将当前锁的线程持有者设为当前线程.
            // 公平锁, 如果队列大小不为0, 则证明已经有线程在等待锁, 进入排队等锁状态.
            // 如果实现非公平锁, 把!hasQueuedPredecessors()去掉即可, 即可以强插队, 在插队失败后再排队.
            if (!hasQueuedPredecessors() && acquire()) {
                exclusiveOwnerThread = currentThread;
            } else {
                acquireFromQueue();
            }
        } else if (currentThread == exclusiveOwnerThread) {
            // 只会有一个线程会执行到这块, 所以i++操作没问题.
            state++;
        } else { // 其他线程为获取到锁, 直接从队列中获取锁.
            acquireFromQueue();
        }

    }

    public boolean hasQueuedPredecessors() {
        return waiterThreadQueue.size() > 0;
    }

    /**
     * 从队列中获取锁.
     */
    public void acquireFromQueue() {
        Thread currentThread = Thread.currentThread();
        // 失败则放入等待队列, 然后自旋+Park, 当被Unpark的时候从线程队列取出第一个线程, 让其获取锁.
        waiterThreadQueue.add(currentThread);

        // 自旋等待被LockSupport.unPark方法唤醒.
        for (int i = 1; ; i++) {
            if ((waiterThreadQueue.size() == 0 || waiterThreadQueue.peek() == currentThread)
                && acquire()) {
                waiterThreadQueue.poll();
                exclusiveOwnerThread = currentThread;
                break;
            } else {
                LockSupport.park();
            }

            System.out.println(Thread.currentThread() + "自旋" + i + "次.");
        }
    }

    public boolean acquire() {
        if (!compareAndSetState(0, 1)) {
            return false;
        } else {
            return true;
        }
    }

    public boolean release() {
        if (!compareAndSetState(1, 0)) {
            return false;
        } else {
            return true;
        }
    }

    public void unlock() {
        // 将state重新置为0, 然后Unpark.
        Thread currentThread = Thread.currentThread();
        if (exclusiveOwnerThread != currentThread) {
            throw new IllegalMonitorStateException();
        }

        if (state == 1) {
            release();
            exclusiveOwnerThread = null;
            if (waiterThreadQueue.size() > 0) {
                Thread releaseThread = waiterThreadQueue.peek();
                LockSupport.unpark(releaseThread);
            }
        } else if (state > 1) {
            state--;
        }

    }

    private boolean compareAndSetState(int expected, int updated) {
        return unsafe.compareAndSwapInt(this, stateValueOffset, expected, updated);
    }

    // 验证锁的正确性.
    public static void main(String[] args) {
        MyFairLock myAqs = new MyFairLock();
        List<String> list = new LinkedList<>();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                // 可重入.
                myAqs.lock();
                myAqs.lock();
                myAqs.lock();

                for (int j = 0; j < 10; j++) {
                    list.add(j + "");
                }
                System.out.println("Size: " + list.size() + "; " + list);

                myAqs.unlock();
                myAqs.unlock();
                myAqs.unlock();
            }, "Thread " + i).start();
        }
    }
}


