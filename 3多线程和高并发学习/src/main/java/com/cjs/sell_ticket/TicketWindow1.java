package com.cjs.sell_ticket;

/**
 * 线程安全的
 * 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月3日-下午3:28:29
 */
// 只能有一个Runnable对象，因为ticketNum是基本数据类型，不能被所有的Runnable对象共享----------------
public class TicketWindow1 implements Runnable{
	int ticketNum = 10;
	
	public void run(){
		while(true){
			if(ticketNum > 0){
				// 只有一个Runnable对象，所以可以解决并发问题
				synchronized(this){
					if(ticketNum > 0){
						System.out.println(Thread.currentThread().getName() + 
								" sell 第 " + ticketNum +"张票");
						ticketNum = ticketNum - 1;
					}
				}
				try {
					// sleep放到monitor region里面不会释放monitor
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
		
		// TicketWindow1 tw2 = new TicketWindow1(); // ----------------------
		// Thread t5 = new Thread(tw2 ,"test window");
		
		t1.start();
		t2.start();
		t4.start();
		t3.start();
		// t5.start();
	}
}

