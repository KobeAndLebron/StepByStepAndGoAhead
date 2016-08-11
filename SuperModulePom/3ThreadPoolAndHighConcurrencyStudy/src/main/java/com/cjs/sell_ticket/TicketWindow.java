package com.cjs.sell_ticket;

/**
 * 非线程安全的
 * 
 * 多线程不安全的根本原因-破环了线程的隔离性：
 * 	多线程在同时访问共享对象的时候，由于线程间相互抢占CPU的控制权，造成一个线程夹在另一个线程的执行过程中执行，所以有可能导致错误的执行结果
 * 
 * 类比事务的话，单线程对应事务的隔离级别为序列化，大大影响 了程序的并发度；
 *
 *
 *	ticketNum的载体-此类-在内存中是单例的所以不用管载体的载体是否为非单例,并且涉及到读写交叉执行,所以需要考虑线程安全问题+++++++
 * @see TicketWindow1 解决了线程安全问题
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月3日-下午3:28:29
 */
public class TicketWindow implements Runnable{
	int ticketNum = 10;
	
	// +++++++
	public void run(){
		while(true){
			if(ticketNum > 0){
				System.out.println(Thread.currentThread().getName() + 
						" sell 第 " + ticketNum +"张票");
				ticketNum--;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else{
				System.out.println(Thread.currentThread().getName() +
						" is sold out");
				return;
			}
		}
	}
	
	public static void main(String[] args) {
		TicketWindow tw = new TicketWindow();
		Thread t1 = new Thread(tw);
		t1.setPriority(Thread.MAX_PRIORITY);
		Thread t2 = new Thread(tw);
		Thread t3 = new Thread(tw);
		t3.setPriority(Thread.MIN_PRIORITY);
		t1.start();
		t2.start();
		t3.start();
	}
}

