package com.cjs.first_practice;

import com.cjs.ThreadLocal.ThreadId;

public class LiftOffThread extends Thread{
	private int countDown = 10;
	private Integer threadId;
	
	// 成员变量的初始化还有构造器还有非静态代码块最后被组合为<init>()方法，所以对象变量的赋值操作都是在主线程中调用
	 // 所以这个threadId1里面的ThreadLocal对象一直在主线程中，不会被放在其他线程中
	private Integer threadId1 = ThreadId.get();
	
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
		return "#" + "CurrentThread: " + this.threadId + "Main: " + this.threadId1 + "(" + (countDown > 0? countDown : "ListOff!") + "), ";
	}
	
	public static void main(String[] args) {
		for(int i = 0; i < 10; i++){
			Thread thread = new LiftOffThread();
			thread.start();
		}
		System.out.println(Thread.currentThread() + "is over");
	}
}
