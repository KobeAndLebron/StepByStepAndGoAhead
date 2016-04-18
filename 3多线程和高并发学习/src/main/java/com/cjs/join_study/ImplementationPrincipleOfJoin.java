package com.cjs.join_study;

import com.cjs.TimerThread;

/**
 * {@linkplain java.lang.Thread#join()}的实现原理-涉及到wait的实现原理
 * 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月11日-下午2:25:07
 */
public class ImplementationPrincipleOfJoin extends Thread{
	private static int t1 = 2000;
	private static int t2 = 10000;
	public void run(){
		try {
			/**
			 * 保障先执行主线程的join方法，而且睡的时间不能比join长，长的话这个测试就失效了。。。
			 */
			Thread.sleep(t1);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		synchronized (this) {
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
		ImplementationPrincipleOfJoin bj = new ImplementationPrincipleOfJoin();
		bj.start();
		try {
			/**
			 * 由于Thread的join方法是同步的，那么调用join会首先获取this的锁,然后进入this的wait set
			 * 如果是join with timeout，那么就是调用wait with timeout，如果到指定时间this代表的线程还没死，那么就直接跳出循环
			 * 如果是join without timeout，那么就是调用wait without timeout,然后就是等直到this代表的线程dead，isAlive（）返回false
			 * 
			 * 当然在wait之后要想重新调用wait之后的方法，那么就要重新获取this的锁，如果在this代表的线程里获取this的锁之后sleep一段时间
			 * -比join的时间长，因为sleep不会释放锁，所以join里面的代码得不到执行。。。。那么就会使join的timeout失效。。。。
			 
			 	one thread must be able to execute a monitor region from beginning to end without another thread 
			 	concurrently executing a monitor region of the same monitor. 
				A monitor enforces this one-thread-at-a-time execution of its monitor regions
			 */
			// As a thread terminates the this.notifyAll method is invoked
			bj.join(5000);
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
