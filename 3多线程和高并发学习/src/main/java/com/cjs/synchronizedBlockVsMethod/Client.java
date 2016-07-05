package com.cjs.synchronizedBlockVsMethod;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
	/**
	 * test the two different approaches
	 */
	public static void testApproaches(PairManager p1, PairManager p2){
		ExecutorService exec = Executors.newCachedThreadPool();
		// 共同使用容器p1的生产者和检查者进程
		PairManipulator m1 = new PairManipulator(p1);
		PairChecker c1 = new PairChecker(p1);

		PairManipulator m2 = new PairManipulator(p2);
		PairChecker c2 = new PairChecker(p2);
		exec.execute(m1);
		exec.execute(m2);
		exec.execute(c1);
		exec.execute(c2);
		
		try{
			Thread.sleep(4000);
		}catch(InterruptedException e){
			
		}
		System.out.println(PairManipulator.class.getName() + "1 :" + m1);
		System.out.println(PairManipulator.class.getName() + "2 :" + m2);
		System.exit(0);
	}
	
	public static void main(String[] args) {
		// 容器1
		PairManager p1 = new PairManager1();
		// 容器2
		PairManager p2 = new PairManager1();
		testApproaches(p1, p2);
	}
}
