package com.cjs.sell_ticket;

/**
 * 线程安全的_最合理的版本<br/>
 * 
 * 可以拥有自己的一些特定属性而{@linkplain TicketWindow1}不可以，因为所有线程共用的是一个Runnable对象
 * 
 * <ol>
 * 	<li>基本上所有的并发模式在解决线程冲突问题的时候，都是采用序列化访问共享资源的方案，这就意味着在给定时刻只允许有一个任务访问 共享资源。 </li>
 * 	<li>通常情况下是通过加锁来实现的，即Mutual Exclusion，使用MUTEX互斥量的方法，Java中是用synchronized关键字实现的</li>
 * </ol>
 * 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月3日-下午3:28:29
 */
// 每一个Runnable对象都代表一个进程-即窗口,可以对方法区里或者堆区的对象操作
public class TicketWindowFinal1 implements Runnable{
	private Resource1 resource;
	// 可以有自己一些特定的属性~
	
	public TicketWindowFinal1(Resource1 resource){
		this.resource = resource;
	}
	
	// 或者通过依赖的方式将资源的引用放到这-后面的生产者消费者就是这样做的~~~
	public void run(){
		while(true){
			if(!this.resource.isEmpty()){
				this.resource.consume();
			}else{
				break;
			}
		}
	}
	
	/**
	 * 载体为非单例而Resource1为单例 +++++++
	 * @param args
	 */
	public static void main(String[] args) {
		Resource1 r = new Resource1();
		/**
		 * new多个对象不会导致每个对象都有一个Method对象，Method对象只会有一份，存于方法区~所以这个版本和TicketWindow1这个版本相比并无内存占用大
		 * 的劣势在方法占用方面,每个Runnable都会有自己的特定的属性
		 */
		TicketWindowFinal1 tw1 = new TicketWindowFinal1(r);
		TicketWindowFinal1 tw2 = new TicketWindowFinal1(r);
		TicketWindowFinal1 tw3 = new TicketWindowFinal1(r);
		TicketWindowFinal1 tw4 = new TicketWindowFinal1(r);
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
 * 这个类是单例-不管载体是不是单例-实际是非单例，所以当存在读写交叉的问题时需要考虑线程安全问题+++++++
 * 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月3日-下午4:15:57
 */
class Resource1{
	int ticketNum = 100;
	
	public boolean isEmpty(){
		return this.ticketNum == 0;
	}
	
	public void consume(){
		if(!isEmpty()){
			// 这里可以使用this，因为内存中只有一个Resource1即单例
			synchronized(this){
				if(!isEmpty()){
					System.out.println(Thread.currentThread().getName() + 
							" sell 第 " + this.ticketNum +"张票");
					this.ticketNum = this.ticketNum - 1;
				}
			}
			try {
				Thread.sleep(100);
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
