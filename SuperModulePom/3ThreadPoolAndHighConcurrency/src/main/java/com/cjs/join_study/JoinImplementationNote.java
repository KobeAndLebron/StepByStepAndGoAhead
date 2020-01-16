package com.cjs.join_study;

import com.cjs.TimerThread;

/**
 * {@linkplain java.lang.Thread#join()}的实现原理, 通过wait方法实现.
 *
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月11日-下午2:25:07
 */
public class JoinImplementationNote extends Thread {
    private static int t1 = 2000;
    private static int t2 = 10000;
    private Thread mainThread;

    public JoinImplementationNote(Thread mainThread) {
        this.mainThread = mainThread;
    }

    public void run() {
        System.out.println("同步前主线程的状态:" + mainThread.getState());
        try {
            /**
             * 保障先执行主线程的join方法，而且睡的时间不能比join长，长的话这个测试就失效了。。。
             * eg: join(500)就会使得sleep不放锁失效...
             */
            Thread.sleep(t1);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        synchronized (this) {
            int i = 7;
            while (i-- > 0) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Case1: 对于join(5000)的case, 在这个Where循环里, 主线程的状态由TIMED_WAITING->BLOCKED状态.
                // Case2: 主线程的状态在这个Where循环里为 WAITING 状态.
                // Case3: 主线程的状态在这个Where循环里为 TERMINATED 状态.
                System.out.println("主线程状态: " + mainThread.getState());
            }
            try {
                Thread.sleep(t2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 这种方式就不会让join的timeout失效
		/*synchronized (Object.class) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
    }

    public static void main(String[] args) {
        // 计时器：大概估计线程的睡眠时间
        TimerThread t = new TimerThread(t1 + t2);
        t.start();
        JoinImplementationNote bj = new JoinImplementationNote(Thread.currentThread());
        bj.start();
        try {
            /**
             *   执行原理: Tread里面的Join方法是同步的(使用Synchronized修饰), 所以调用线程对象的Join方法会首先获取this(bj)的Monitor,
             * 然后在Join方法里面调用bj.wait()或者bj.wait(mills)方法:
             *   > 两者都会使Main线程进入阻塞状态.
             *   1. 调用bj.wait(mills)方法(主线程进入{@linkplain State#TIMED_WAITING}状态):
             *   表示在过mills时间后, Main线程会被唤醒, Join方法继续执行, 当join方法被唤醒的时候, 需要再次获取锁(bj的Monitor),
             * 此时如果在另一个线程获取了bj的Monitor而不释放, 就会导致Join方法执行不下去, 进而导致Join方法失效.
             *   2. 调用bj.wait()方法(主线程进入{@linkplain State#WAITING}状态):
             *   直到bj线程执行完毕, 当前线程才会往下走. 当bj线程结束的时候, 会调用this(bj)的notifyAll方法, 进而将
             * Main线程唤醒, 继续执行Join方法, 但是此时bj线程已死(terminate), 所以跳出循环, Join方法结束, 继续执行Main线程.
             *   > Harvest: As a thread terminates the notifyAll method is called
             *
             */
            // Case1: Main线程在5s内被唤醒, 但是由于被唤醒后获取不到bj的Monitor, 所以还是处于阻塞状态(BLOCKED状态). Main线程不能继续执行.
            bj.join(5000);

            // Case2: Main线程一直处于WAITING状态, 直到bj线程结束; bj线程结束会调用this.notifyAll方法.
//            bj.join();

            // Case3: Main线程在1s内被唤醒, 唤醒之后能获取到bj的Monitor, 继续往下执行.
//            bj.join(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /**
         * Alive():Tests if this thread is alive,a thread is alive if it has been started and has not yet dead-run() is
         * over or throw(s) exception.
         */
        System.out.println("I wanted CPU!!! but bj Thread is " + (bj.isAlive() ? "alive" : "dead"));
    }
}
