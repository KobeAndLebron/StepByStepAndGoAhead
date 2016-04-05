package com.cjs.sell_ticket;

/**
 * 线程安全的
 * 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月3日-下午3:28:29
 */
// 每一个Runnable对象都代表一个进程-即窗口,可以对方法区里或者堆区的对象操作
public class TicketWindow1 implements Runnable{
	int ticketNum = 10;
	
	public void run(){
		while(true){
			if(ticketNum > 0){
				synchronized(TicketWindow1.class){
					if(ticketNum > 0){
						System.out.println(Thread.currentThread().getName() + 
								" sell 第 " + ticketNum +"张票");
						ticketNum = ticketNum - 1;
					}
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else if(ticketNum == 0){
				System.out.println(Thread.currentThread().getName() +
						" is sold out");
				return;
			}
		}
	}
	
	public static void main(String[] args) {
		TicketWindow1 tw1 = new TicketWindow1();
		Thread t1 = new Thread(tw1 ,"window 1");
		Thread t2 = new Thread(tw1 ,"window 2");
		Thread t3 = new Thread(tw1 ,"window 3");
		Thread t4 = new Thread(tw1 ,"window 4");
		
		t1.start();
		t2.start();
		t4.start();
		t3.start();
	}
}

