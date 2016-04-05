package com.cjs.sell_ticket;

/**
 * 线程安全的_最合理的版本
 * 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月3日-下午3:28:29
 */
// 每一个Runnable对象都代表一个进程-即窗口,可以对方法区里或者堆区的对象操作
public class TicketWindowFinal implements Runnable{
	/**
	 * 可以自己窗口的一些特定属性而{@linkplain TicketWindow1}不可以，因为所有线程共用的是一个Runnable对象
	 */
	public void run(){
		while(true){
			if(Resource.ticketNum > 0){
				// 如果是sychronized(this)的话，那么就是线程不安全的，因为每个对象拿的是自己的锁，其他对象还是可以进去同步代码块，和没加一样。。。
				// 而TicketFinal.class/Object.class在内存只有一个！！！
				synchronized(Object.class){
					if(Resource.ticketNum > 0){
						System.out.println(Thread.currentThread().getName() + 
								" sell 第 " + Resource.ticketNum +"张票");
						Resource.ticketNum = Resource.ticketNum - 1;
					}
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else if(Resource.ticketNum == 0){
				System.out.println(Thread.currentThread().getName() +
						" is sold out");
				return;
			}
		}
	}
	
	public static void main(String[] args) {
		TicketWindowFinal tw1 = new TicketWindowFinal();
		TicketWindowFinal tw2 = new TicketWindowFinal();
		TicketWindowFinal tw3 = new TicketWindowFinal();
		TicketWindowFinal tw4 = new TicketWindowFinal();
		Thread t1 = new Thread(tw1 ,"window 1");
		Thread t2 = new Thread(tw2 ,"window 2");
		Thread t3 = new Thread(tw3 ,"window 3");
		Thread t4 = new Thread(tw4 ,"window 4");
		t1.start();
		t2.start();
		t4.start();
		t3.start();
	}
}

/**
 * 资源类，初始化后位于内存的方法区，可以被线程共享
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月3日-下午4:15:57
 */
class Resource{
	static int ticketNum = 100;
}
