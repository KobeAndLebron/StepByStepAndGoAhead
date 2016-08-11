package com.cjs.单例模式;

import org.junit.Assert;
import org.junit.Test;

import com.cjs.util.ExecuteResult;

/**
 *  每一个方法(annotated by @test)有多个线程，他们可以并行执行,但是多个方法的多个线程不能并行执行(Junit的运行原理如下)
 *  ,并且因为他们公用的是Singeton3的静态实例,所以要Sleep一段时间,保证方法的多个线程执行完毕
 * 
 * 	Junit原理：所有的测试方法都是在Main线程中顺序/序列化执行的
 * 依据：下面的测试方法输出ThreadName的都是main
 * 
 * -如果不进行并发控制的话-多个测试方法顺序执行,测试方法内的多个线程并发执行-导致:
 * 第一个测试方法执行完毕-还有几个线程在并发执行
 * 第二个测试方法执行完毕-还有几个线程在并发执行
 * 这样就导致了多个方法的线程并发执行，形成干扰
 * ....
 * 
 * @author ChenJingShuai  
 *
 * Make a bit of progress every day. 2016年5月21日-上午11:08:55
 *
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
					ExecuteResult.setFalseResult();
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
					ExecuteResult.setFalseResult();
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
	 * @throws InterruptedException 
	 * @see Singeton3#getInstance1()
	 */
	@Test
	public void testGetInstance1() throws InterruptedException{
		int requestTheadNum = 6;
		ExecuteResult.init();
		System.out.println(Thread.currentThread().getName());
		Thread thread1 = null;
		for(int i = 0; i < requestTheadNum;i++){
			thread1 = new Thread(run2);
			thread1.setDaemon(true);
			thread1.start();
		}
		if (!ExecuteResult.isFinish(requestTheadNum)) {
			Thread.sleep(10);
		}
		if(ExecuteResult.ifSuccess()){
			Assert.assertEquals(true, true);
		}else{
			Assert.assertEquals(true, false);
		}
		Singeton3.init();
	}
	
	/**
	 * @see Singeton3#getInstance2()
	 */
	@Test
	public void testGetInstance2(){
		System.out.println(Thread.currentThread().getName());
		Thread thread1 = null;
		for(int i = 0; i < 6;i++){
			thread1 = new Thread(run3);
			thread1.setDaemon(true);
			thread1.start();
		}
		while(thread1.isAlive()){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Singeton3.init();
	}
	
	/**
	 * @throws InterruptedException 
	 * @see Singeton3#getInstance()
	 */
	@Test
	public void testGetInstance() throws InterruptedException{
		int requestTheadNum = 2;
		ExecuteResult.init();
		System.out.println(Thread.currentThread().getName());
		Thread thread1 = null;
		for(int i = 0; i < requestTheadNum;i++){
			thread1 = new Thread(run1);
			thread1.start();
		}
		if (!ExecuteResult.isFinish(requestTheadNum)) {
			Thread.sleep(10);
		}
		if(ExecuteResult.ifSuccess()){
			Assert.assertEquals(true, true);
		}else{
			Assert.assertEquals(true, false);
		}
		Singeton3.init();
	}
}
