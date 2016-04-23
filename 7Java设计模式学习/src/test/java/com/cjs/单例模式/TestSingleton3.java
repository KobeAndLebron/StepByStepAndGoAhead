package com.cjs.单例模式;

import org.junit.Test;

/**
 *  一个方法有多个线程，他们可以并行执行,但是多个方法的多个线程不能并行执行,
 * 因为他们公用的是Singeton3的静态实例,所以要Sleep一段时间,保证方法的多个线程执行完毕
 * 
 * 所有的测试方法都是在Main线程中顺序/序列化执行的,下面的测试方法输出ThreadName的都是main
 * 
 * -如果不Sleep的话，
 * 第一个测试方法执行完毕-还有几个线程在并发执行
 * 第二个测试方法执行完毕-还有几个线程在并发执行
 * ....
 * 加上的话，那么Sleep一段时间此方法才会结束，这时候并发的几个线程就会结束
 * 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月23日-下午7:09:49
 */
public class TestSingleton3 {
	final SingletonChecker checker = new SingletonChecker();
	Runnable run1 = new Runnable(){
		public void run() {
			// Singeton3 s = Singeton3.getInstance();
			/**
			 * 会将每一个产生的Sinleton3实例都放入到栈内存中,所以每个task所使用的是不同的实例
			 */
			if(!checker.check(Singeton3.getInstance())){
				synchronized (this) {
					System.out.println("Not Thread-Safe!!!");
					System.out.println(checker);
				}
			}
		}
		
	};
	final SingletonChecker checker1 = new SingletonChecker();
	Runnable run2 = new Runnable(){
		public void run() {
			if(!checker1.check(Singeton3.getInstance1())){
				synchronized (this) {
					System.out.println("Not Thread-Safe!!!");
					System.out.println(checker1);
				}
			}
		}
		
	};
	final SingletonChecker checker2 = new SingletonChecker();
	Runnable run3 = new Runnable(){
		public void run() {
			if(!checker2.check(Singeton3.getInstance2())){
				synchronized (this) {
					System.out.println("Not Thread-Safe!!!");
					System.out.println(checker2);
				}
			}
		}
		
	};
	/**
	 * @see Singeton3#getInstance1()
	 */
	@Test
	public void testGetInstance1(){
		System.out.println(Thread.currentThread().getName());
		for(int i = 0; i < 6;i++){
			Thread thread1 = new Thread(run2);
			thread1.setDaemon(true);
			thread1.start();
		}
		try {
			// 程序运行4秒即结束,因为其他线程都是守护进程
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Singeton3.init();
	}
	
	/**
	 * @see Singeton3#getInstance2()
	 */
	@Test
	public void testGetInstance2(){
		System.out.println(Thread.currentThread().getName());
		for(int i = 0; i < 6;i++){
			Thread thread1 = new Thread(run3);
			thread1.setDaemon(true);
			thread1.start();
		}
		try {
			// 程序运行4秒即结束,因为其他线程都是守护进程
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Singeton3.init();
	}
	
	/**
	 * @see Singeton3#getInstance()
	 */
	@Test
	public void testGetInstance(){
		System.out.println(Thread.currentThread().getName());
		System.out.println("------------------");
		for(int i = 0; i < 6;i++){
			Thread thread1 = new Thread(run1);
			thread1.setDaemon(true);
			thread1.start();
		}
		try {
			// 程序运行4秒即结束,因为其他线程都是守护进程
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Singeton3.init();
		System.out.println("------------------");
	}
}
