package com.cjs.join_study;

/**
 *  Purpose of Join:<br/>
 *  &nbsp;&nbsp;&nbsp;使得当前线程进入{@linkplain Thread.State#WAITING Waiting}/
 *  {@linkplain Thread.State#TIMED_WAITING TIMED_WAITING}直到被join的进程Dead或者超时-the case using join with time out,
 *  才进入可执行状态
 * 
 * @see Thread#join()
 * @see Thread#join(long)
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
			 * Calling join() with timeout will cause the current thread into the state called TIMED_WAITING,
			 * 如果timeout到时被join的进程还没执行完，那么当前线程变为阻塞状态
			 * (in JVM not in operating system，in operating system TIMED_WAITING is also
			 * belong to Blocked state)，等待进入Runnable状态然后分配CPU执行.
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
