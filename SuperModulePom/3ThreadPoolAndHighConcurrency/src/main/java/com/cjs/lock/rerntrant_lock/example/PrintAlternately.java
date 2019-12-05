package com.cjs.lock.rerntrant_lock.example;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程之间按顺序调用,  实现A->B->C三个线程启动[ABC线程各10个], 要求如下:
 *
 * AA打印5次, BB打印10次, CC打印15次
 * 紧接着
 * AA打印5次, BB打印10次, CC打印15次.
 * ...
 * 循环10轮.
 */
public class PrintAlternately {
    private static Resolution resolution = new Resolution();

    public static void main(String[] args) {
        for (int j = 0; j < 10; j++) {
            new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    resolution.print5();
                }
            }, "A").start();

            new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    resolution.print10();
                }
            }, "B").start();

            new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    resolution.print15();
                }
            }, "C").start();
        }
    }

    private static class Resolution {
        int i = 0; // 0线程A 1线程B 2线程C
        private Lock lock = new ReentrantLock();
        private Condition conditionA = lock.newCondition();
        private Condition conditionB = lock.newCondition();
        private Condition conditionC = lock.newCondition();

        private void print5() {
            lock.lock();

            try {
                while (i != 0) {
                    conditionA.await();
                }

                for (int j = 0; j < 5; j++) {
                    System.out.print("A" + " ");
                }
                System.out.println();

                i = 1;

                // 唤醒B线程.
                conditionB.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }

        private void print10() {
            lock.lock();

            try {
                while (i != 1) {
                    conditionB.await();
                }

                for (int j = 0; j < 10; j++) {
                    System.out.print("B" + " ");
                }
                System.out.println();

                i = 2;

                // 唤醒线程C.
                conditionC.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }

        private void print15() {
            lock.lock();

            try {
                while (i != 2) {
                    conditionC.await();
                }

                for (int j = 0; j < 15; j++) {
                    System.out.print("C" + " ");
                }
                System.out.println();

                i = 0;

                // 唤醒线程A.
                conditionA.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }

    }
}
