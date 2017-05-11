package com.cjs.wait_notify;

/**
 * 111
 * 222
 * 111
 * 222
 * Created by chenjingshuai on 17-4-27.
 */
public class PrintInTurnForMultiThreads {
    public static void main(String[] args) {
        PrintThread printThread = new PrintThread();
        BackUp1 backUp1 = new BackUp1(printThread);
        BackUp2 backUp2 = new BackUp2(printThread);
        for (int i = 0; i < 10; i++) {
            Thread thread1 = new Thread(backUp1);
            thread1.start();
            Thread thread2 = new Thread(backUp2);
            thread2.start();
        }
    }

}

class PrintThread {
    private boolean flag = false;

    synchronized void print1() throws InterruptedException {
        while (flag) {
            this.wait();
        }
        print(1);
        flag = true;
        this.notifyAll();
    }

    synchronized void print2() throws InterruptedException {
        while (!flag) {
            this.wait();
        }
        print(2);
        flag = false;
        this.notifyAll();
    }

    private void print(int numToPrint) {
        for (int i = 0; i < 10; i++) {
            System.out.print(numToPrint);
        }
        System.out.println();
    }

}

abstract class BackUp {
    PrintThread printThread;

    BackUp(PrintThread printThread) {
        this.printThread = printThread;
    }
}

class BackUp1 extends BackUp implements Runnable {

    BackUp1(PrintThread printThread) {
        super(printThread);
    }

    @Override
    public void run() {
        try {
            printThread.print1();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}



class BackUp2 extends BackUp implements Runnable {

    BackUp2(PrintThread printThread) {
        super(printThread);
    }

    @Override
    public void run() {
        try {
            printThread.print2();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}