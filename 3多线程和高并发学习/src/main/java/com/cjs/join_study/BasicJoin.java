package com.cjs.join_study;

/**
 * 使得线程进入阻塞状态直到被join的进程执行完毕-即dead或者在超时的情况下-use join with time out，才进入可执行状态
 * 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月3日-下午4:49:40
 */
public class BasicJoin extends Thread{
	int i = 10000;
	
	public void run(){
		while(i-- > 0){
			System.out.println(Thread.currentThread().getName() + " is processed");
		}
	}
	
	public static void main(String[] args) {
		BasicJoin bj = new BasicJoin();
		bj.start();
		try {
			/**
			 * calling join() with timeout will cause the current thread into TIMED_WAITING,如果timeout到时被join的进程
			 * 还没执行完，那么当前线程变为阻塞状态(in JVM not in operating system，in operating system TIMED_WAITING is also
			 * belong to Blocked state)，等待进入Runnable状态分配CPU.
			 */
			bj.join(1);
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
