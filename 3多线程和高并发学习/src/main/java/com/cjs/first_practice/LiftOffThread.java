package com.cjs.first_practice;

import com.cjs.ThreadLocal.ThreadId;

/**
 * 涉及到{@linkplain com.cjs.ThreadLocal.ThreadId}以及线程的作用域
 * 
 * 因为{@code countDown}的载体LiftOffThread在内存中为非单例并且本身就是Thread对象,所以即使存在读写交叉的问题,也不需要考虑线程安全问题+++++++
 * 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月15日-下午12:35:48
 */
public class LiftOffThread extends Thread{
	private int countDown = 10;
	private Integer threadId;
	
	// 成员变量的初始化还有构造器还有非静态代码块最后被组合为<init>()方法，所以对象变量的赋值操作都是在主线程中调用
	// 所以这个threadId1里面的ThreadLocal对象一直在主线程中，不会被放在其他线程中
	private Integer threadId1 = ThreadId.get();
	
	/**
	 * 只有一个线程/Runnable对象的run方法才可以并发执行，其余的方法都在启动它的线程的方法栈里执行
	 */
	public void run(){
		threadId = ThreadId.get();
		while(countDown-- >0){
			System.out.println(status());
			/**********************************************/
			// 使得CPU对各个线程的时间片分配更加均匀
			/**********************************************/
		    Thread.yield();
			/*try{
				Thread.sleep(100);
			}catch (InterruptedException e) {
				e.printStackTrace();
			}*/
		}
	}
	
	public String status(){
		return "#" + "CurrentThread(name: + " + currentThread().getName()  
				+ "): " + this.threadId + " ,Main: " + this.threadId1 + "(" + (countDown > 0? countDown : "ListOff!") + "), ";
	}
	
	public static void main(String[] args) {
		for(int i = 0; i < 10; i++){
			Thread thread = new LiftOffThread();
			thread.start();
		}
		System.out.println(Thread.currentThread() + "is over");
	}
}
