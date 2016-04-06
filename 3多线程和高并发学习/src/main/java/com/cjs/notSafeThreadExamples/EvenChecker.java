package com.cjs.notSafeThreadExamples;

/**
 * 	跟{@linkplain com.cjs.sell_ticket.TicketWindowFinal}一样，每一个线程都是对应一个对象，然后每个线程对象都持有共同的资源，由于Ticket里面
 * 的票数属性不是引用类型，所以每一个对象里面都只是基本数据类型的值，每个Runnable对象访问的只是自己的资源；
 * 所以要将票数包装为一个类,然后所有的Runnable对象都引用这个类，这样就可以达到所有Runnable对象持有共同的资源~~~
 * 
 * 引用是一种资源，基本数据类型的值也是资源~~~存储方式不一样导致处理不一样-栈/堆
 * 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月6日-上午8:28:43
 */
public class EvenChecker implements Runnable{
	private IntGenerator generator;
	private final int id;

	public EvenChecker(IntGenerator generator ,int id){
		this.generator = generator;
		this.id = id;
	}
	
	public void run() {
		while(!generator.isCanceled()){
			int val = generator.next();
			if(val % 2 != 0){
				System.out.println(val + "not even");
				generator.cancel();
			}
		}
	}
	
	// Test any type of IntGenerator
	public static void test(IntGenerator gp ,int count){
		System.out.println("Press Control-C to exit");
		for(int i = 0; i < count; i++){
			Thread t = new Thread(new EvenChecker(gp, i));
			t.start();
		}
	}
	
	// Default value for count
	public static void test(IntGenerator gp){
		test(gp, 10);
	}
}
