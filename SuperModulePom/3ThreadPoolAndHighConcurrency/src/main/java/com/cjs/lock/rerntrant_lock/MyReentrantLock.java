package com.cjs.lock.rerntrant_lock;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * ReentrantLock源码简单解析.
 */
public class MyReentrantLock {

    public abstract class Sync extends AbstractQueuedSynchronizer {
        public abstract void lock();

        public void unlock() {
            super.release(1);
        }

        protected final boolean tryRelease(int releases) {
            int c = getState() - releases;
            if (Thread.currentThread() != getExclusiveOwnerThread())
                throw new IllegalMonitorStateException();
            boolean free = false;
            if (c == 0) {
                free = true;
                setExclusiveOwnerThread(null);
            }
            setState(c);
            return free;
        }
    }

    public class FairSync extends Sync {
        @Override
        protected boolean tryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {
                if (!hasQueuedPredecessors() &&
                    compareAndSetState(0, acquires)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            } else if (current == getExclusiveOwnerThread()) {
                int nextc = c + acquires;
                if (nextc < 0)
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;
        }

        /**
         * 公平锁在Lock的时候, 直接调用AQS的acquire方法, acquire方法调用了模版方法: {@linkplain #tryAcquire(int)},
         *
         * 公平性的体现: 在模版方法中, 如果锁被占用, 则判断FIFO队列中是否有节点在等待锁, 如果没有才去CAS获取锁, 获取失败直接返回false,
         * 去队尾等待.
         */
        @Override
        public void lock() {
            super.acquire(1);
        }
    }

    public class NoFairSync extends Sync {
        @Override
        protected boolean tryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {
                if (compareAndSetState(0, acquires)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            } else if (current == getExclusiveOwnerThread()) {
                int nextc = c + acquires;
                if (nextc < 0) // overflow
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;
        }

        /**
         * 非公平性的体现:
         *  上来直接CAS获取锁, 如果获取失败, 则调用acquire方法再次获取锁, 在模版方法tryAcquire中, 也不判断当前持有锁的线程是否有节点在
         *  等待, 直接CAS获取锁, 获取失败则去队尾等待.
         */
        @Override
        public void lock() {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
            } else {
                acquire(1);
            }
        }
    }

}
