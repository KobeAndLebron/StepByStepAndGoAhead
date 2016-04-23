package com.cjs.synchronizedBlockVsMethod;

public class PairManager2 extends PairManager{
	/**
	 * 将此线程产生的p放入到栈中，因为栈是线程所有的，所以可以解决p的存储线程安全问题
	 */
	public void increment() {
		Pair temp;
		synchronized (this) {
			p.incrementX();
			p.incrementY();
			temp = p;
		}
		store(temp);
	}
	
	/**
	 * 这种方式虽然解决了++和对于约束条件的线程安全问题,但是有可能一个线程存的不是自己所产生的${@linkplain Pair}在他的载体PairManager是单例的情况下
	 * 相当于先写读
	 */
	public void increment1() {
		synchronized (this) {
			p.incrementX();
			p.incrementY();
		}
		store(p);
	}
}
