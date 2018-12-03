package com.cjs.notSafeThreadExamples.first;

public class ThreadSafeEvenGenerator extends IntGenerator {
	private int currentEvenValue = 0;
	
	// 一个任务有可能在另一个任务执行第一个对++操作但是没有执行第二步的时候，调用next方法
	// 由于所有的Runnable对象共用一个IntGenerator，所以只需要对临界区-The monitor region使用IntGenerator对象的锁即可，
	// 即直接对方法加Synchronized即可
	@Override
	public synchronized int next() {
		/**
		 *  ++操作不是原子性的，线程不安全的???
		 */
		++currentEvenValue; // Danger point here~
		Thread.yield();
		++currentEvenValue;
		return currentEvenValue;
	}

	public static void main(String[] args) {
		ThreadSafeEvenGenerator eg = new ThreadSafeEvenGenerator();
		EvenChecker.test(eg, 2);
	}
}
