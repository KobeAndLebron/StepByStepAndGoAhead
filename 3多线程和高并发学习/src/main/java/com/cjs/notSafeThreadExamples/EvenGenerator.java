package com.cjs.notSafeThreadExamples;

public class EvenGenerator extends IntGenerator {
	private int currentEvenValue = 0;
	
	// 一个任务有可能在另一个任务执行第一个对++操作但是没有执行第二步的时候，调用next方法
	@Override
	public int next() {
		/**
		 *  ++操作不是原子性的，线程不安全的???
		 */
		++currentEvenValue; // Danger point here~
		Thread.yield();
		++currentEvenValue;
		return currentEvenValue;
	}

	public static void main(String[] args) {
		EvenGenerator eg = new EvenGenerator();
		EvenChecker.test(eg, 2);
	}
}
