package com.cjs.wait_notify;

/**
 * 两个线程交叉打印1234....

 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月5日-下午3:28:34
 */
public class PrintInTurn {
	public static void main(String[] args) {
		Runnable runnable = new PrintInTurnThreadTarget();
		Thread t1 = new Thread(runnable);
		Thread t2 = new Thread(runnable);
		t1.start();
		t2.start();
		/*while(true){
			System.out.println("t1 :" + t1.getState());
			System.out.println("t2 :" + t2.getState());
		}*/
	}
}

class PrintInTurnThreadTarget implements Runnable{
	private int startNum = 1;
	
	public void run() {
		synchronized(this){
			while(startNum <= 100){
				System.out.println(Thread.currentThread().getName() + "    " + startNum);
				startNum++;
				notifyAll();
				try {
					wait();
					/**
					 *  wait with timeout: In JVM,threads can optionally specify a timeout when they
					 * execute a wait method.If a thread does specify a timeout,and no other thread 
					 * execute a notify before the timeout expires,the waiting thread in effect receives
					 * an automatic notify from JVM.After the timeout expires,the waiting thread will be 
					 * resurrected even if no other thread has executed an explicit notify.
					 */
					// wait(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			// 去掉这句话将导致程序结束不了........
			notifyAll();
			// 或者将wait改为定时的~~~
		}
	}
	
}
