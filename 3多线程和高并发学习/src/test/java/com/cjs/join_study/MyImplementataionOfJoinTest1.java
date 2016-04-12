package com.cjs.join_study;

/**
 * 自己实现的join方法-测试1:
 * 	当线程sleep（并且拿着自己的锁）的时间大于join的时间的时候，join的时间就会失效；因为join和run方法公用的是一把锁:线程对象
 * 
 * 	让join的timeout失效的方法
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月12日-下午9:17:17
 */
public class MyImplementataionOfJoinTest1 {
	private static final Thread t = new Thread(new Runnable() {
		public void run() {
			try {
				synchronized (t) {
					Thread.sleep(10000);
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
				while(thread.isAlive()){
					thread.wait(0);
				}
			}else{
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
		t.start();
		MyImplementataionOfJoinTest1.join(t, 3000);
		System.out.println("I wanted CPU!!! but bj Thread is " + (t.isAlive() ? "alive" : "dead"));
	}

}
