package com.cjs.notSafeThreadExamples.first;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 机器级别上的原子性。对于常规编程来说很少派上用场，但是在幸能调优方面就有用武之地了
 * 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月22日-下午7:38:13
 */
public class AtomicEvenGenerator extends IntGenerator{
	private static AtomicInteger ac = new AtomicInteger(0);

	public int next() {
		// 保证这个类的方法的线程安全+++++++
		return ac.getAndAdd(2);
	}

	public static void main(String[] args) {
		AtomicEvenGenerator eg = new AtomicEvenGenerator();
		EvenChecker.test(eg, 5);
	}
}
