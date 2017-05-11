package com.cjs.wait_notify;

/**
 * Test: 在执行同步代码块的过程中, 遇到异常而导致代码执行跳出临界区, 锁也会被释放.
 *
 * Created by chenjingshuai on 17-4-27.
 */
public class TestReleaseLockWhenExceptionOccurs {
    public static void main(String[] args) throws InterruptedException {
        TestExceptionThread testExceptionThread = new TestExceptionThread();
        Thread thread = new Thread(testExceptionThread);
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();

        Thread thread1 = new Thread(testExceptionThread);
        thread1.start();
    }
}

class TestExceptionThread implements Runnable {
    @Override
    public void run() {
        try {
            testException();
        } catch (InterruptedException e) {
            System.out.println("InterruptedException occurs.");
            try {
                System.out.println("I am sleeping, but i have released object lock.");
                Thread.sleep(3000);
            } catch (InterruptedException e1) {
            }
            e.printStackTrace();
        }
    }

    private synchronized void testException() throws InterruptedException {
        System.out.println("Get object lock." + Thread.currentThread().getId());
        Thread.sleep(3000);
    }
}

