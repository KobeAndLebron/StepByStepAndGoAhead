package com.cjs.join_study;

import com.cjs.TimerThread;

/**
 * 自己实现的join方法-测试2:
 * 	测试源代码和自己实现的代码中while的作用------
 * 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月12日-下午9:17:17
 */
public class MyImplementataionOfJoinTest2 {
	private static final int t1 = 1000;
	private static final int t2 = 2000;
	
	private static final Thread t = new Thread(new Runnable() {
		public void run() {
			try {
				/**
				 * 保障join方法先执行以保证测试效果
				 */
				Thread.sleep(t1);
				synchronized (t) {
					System.out.println("Sencond");
					/**
					 *  This thread will notify all threads in waiting set which waiting this monitor and continue 
					 * executing the monitor region.When it release the monitor,all threads in the waiting set and 
					 * the entry set will compete the monitor;So it is also called Signal and Continue monitor;
					 */
					t.notifyAll();
					// without timeout 将会造成程序停不下来。。。。。。
					t.wait(t2);
					System.out.println("Test");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	});
	
	public static void join(Thread thread , long timemills) throws InterruptedException{
		synchronized (thread) {
			// 现在的时间
			long now = System.currentTimeMillis();
			// 已经等待的时间
			long waitedTime = 0;
			
			if(timemills < 0){
				throw new IllegalArgumentException("time out is negative");
			}
			
			if(timemills == 0){
				/**
				 * -------
				 */
				/*while(thread.isAlive()){
					thread.wait(0);
				}*/
				System.out.println("Wait firstly");
				thread.wait(0);
			}else{
				/**
				 * -------
				 * 同理，如果wait过程中，被notify的话，没有while的话会让join失效~~
				 */
				while(thread.isAlive()){
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
	
	public static void main(String[] args) throws InterruptedException {
		TimerThread timer = new TimerThread(t1 + t2);
		timer.start();
		t.start();
		MyImplementataionOfJoinTest2.join(t, 0);
		System.out.println("I wanted CPU!!! but bj Thread is " + (t.isAlive() ? "alive" : "dead"));
	}

}
