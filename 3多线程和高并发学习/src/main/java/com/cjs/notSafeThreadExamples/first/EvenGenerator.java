 package com.cjs.notSafeThreadExamples.first;

/**
 * 因为currentEvenValue的载体这个类在内存的使用是单例,所以调用它的类在(这里是${@linkplain EvenChecker})是不是单例,并且存在读写交叉问题,
 * 所以需要考虑多线程安全问题+++++++
 * 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月16日-下午2:39:18
 */
public class EvenGenerator extends IntGenerator {
	private int currentEvenValue = 0;
	
	/**
	 * 此函数的作用是产生下一个偶数，下面的读写问题是基于他的作用产生的
	 * 一个任务有可能在另一个任务执行第一个对++操作但是没有执行第二步的时候，调用next方法，这时候就生成了一个奇数，导致错误
	 * 一个任务写的过程中被另一个写的任务所干扰	+++++++
	   并且由于这个类是单例的,所以只需在方法加上synchronized关键字即可,他会获取this代表的Monitor
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
