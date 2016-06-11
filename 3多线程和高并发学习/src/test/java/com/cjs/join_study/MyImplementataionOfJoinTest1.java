package com.cjs.join_study;

import org.junit.Test;

import com.cjs.TimerThread;

/**
 * 自己实现的join方法-测试1-让join的timeout失效的方法:
 * 	当线程sleep（并且拿着自己的锁）的时间大于join的时间的时候，join的时间就会失效；因为join和run方法公用的是一把锁:此线程对象
 * 
 * @see ImplementationPrincipleOfJoin
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月12日-下午9:17:17
 */
public class MyImplementataionOfJoinTest1 {
	// 用于保证先执行join方法
	private static final int mills = 1000;
	// 线程sleep的时间
	private static final int mills1 = 10000;
	private static final Thread t = new Thread(new Runnable() {
		public void run() {
			try {
				// 保证先执行t的join方法
				Thread.sleep(mills);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			try {
				synchronized (t) {
					Thread.sleep(mills1);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	});
	
	public static void join(Thread thread, long timemills) throws InterruptedException{
		synchronized (thread) {
			// 现在的时间
			long now = System.currentTimeMillis();
			// 已经等待的时间
			long waitedTime = 0;
			
			if(timemills < 0){
				throw new IllegalArgumentException("Time out is negative");
			}
			
			if(timemills == 0){
				while(thread.isAlive()){
					thread.wait(0);
				}
			}else{
				while(thread.isAlive()){
					// 所剩的时间
					long delay = timemills - waitedTime;
					if(delay <= 0){
						break;
					}
					thread.wait(delay);
					waitedTime = System.currentTimeMillis() - now;
				}
			}
		}
	}
	
	@Test
	public void test() throws InterruptedException {
		TimerThread th = new TimerThread(mills + mills1);
		th.start();
		t.start();
		// 3000ms expires
		MyImplementataionOfJoinTest1.join(t, 3000);
	}

}
