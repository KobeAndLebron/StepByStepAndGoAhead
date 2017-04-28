package com.cjs.join_study;

/**
 *
 * Created by chenjingshuai on 17-4-28.
 */
public class Practice1 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread1();
        Thread thread2 = new Thread2(thread1);
        thread1.start();
        thread2.start();
        thread1.join(200);
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
    final Thread thread1;

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
