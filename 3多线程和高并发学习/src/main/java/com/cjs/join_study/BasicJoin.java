package com.cjs.join_study;

/**
 * 使得线程进入阻塞状态直到被join的进程执行完毕-即dead，才进入可执行状态
 * 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月3日-下午4:49:40
 */
public class BasicJoin extends Thread{
	int i = 10;
	
	public void run(){
		while(i-- > 0){
			System.out.println(Thread.currentThread().getName() + "is processed");
		}
	}
	
	public static void main(String[] args) {
		BasicJoin bj = new BasicJoin();
		bj.start();
		try {
			bj.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("I wanted CPU!!! but bj Thread is " + (bj.isAlive() ? "alive" : "dead"));
	}
}
