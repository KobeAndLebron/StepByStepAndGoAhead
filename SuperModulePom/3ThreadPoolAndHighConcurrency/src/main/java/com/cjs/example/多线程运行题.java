package com.cjs.example;

public class 多线程运行题 {
    private static int index;

    class MyThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("run" + index++);
        }

        @Override
        public synchronized void start() {
            // 父类里面的start方法会将当前线程启动, 使得当前线程进入就绪状态.
            super.start();
            System.out.println("start" + index);
        }
    }

    public static void main(String[] args) {
        MyThread myThread = new 多线程运行题().new MyThread();
        index++;
        myThread.start();
        index++;
        myThread.run();
    }
}

