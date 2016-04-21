package com.cjs.notSafeThreadExamples.second;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ${@code i}的载体在内存是单例,并且存在读写交叉问题(在方法的配套使用情况下),所以需要考虑线程安全问题+++++++
 *  
 * 不能盲目地利用原子性的特性
 * 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月16日-下午3:06:05
 */
public class AtomicityTest implements Runnable{
	private int i = 0;
	
	// 去掉Sychronized关键字程序就有线程安全问题~：在evenIncrement方法执行到第一个i++之后，就执行getValue方法
	public /*synchronized*/ int getValue(){
		return this.i;
	}
	
	private synchronized void evenIncrement(){
		i++;
		i++;
	}
	
	public void run(){
		while(true){
			evenIncrement();
		}
	}
	
	public static void main(String[] args) {
		ExecutorService es  = Executors.newCachedThreadPool();
		AtomicityTest at = new AtomicityTest();
		es.execute(at);
		while(true){
			int val = at.getValue();
			if(val % 2 != 0){
				System.out.println(val);
				System.exit(0);
			}
		}
	}
}

