package com.cjs.notSafeThreadExamples.first;

/**
 * 这个类在内存的使用是单例,不管载体Runnable是不是单例-实际为非单例,所以需要考虑多线程安全问题+++++++
 * 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月16日-下午2:39:18
 */
public class EvenGenerator extends IntGenerator {
	private int currentEvenValue = 0;
	
	/**
	 * 一个任务有可能在另一个任务执行第一个对++操作但是没有执行第二步的时候，调用next方法，这时候就生成了一个奇数，导致错误
	 * 一个任务写的过程中被另一个写的任务所干扰	+++++++
	 */
	@Override
	public int next() {
		/**
		 *  ++操作不是原子性的，线程不安全的;There is a solution on 
		 *  {@linkplain com.cjs.notSafeThreadExamples.third.SerialNumberGenerator} for this question.
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
