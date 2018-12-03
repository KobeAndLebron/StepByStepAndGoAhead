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
			temp = getPair();
		}
		store(temp);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(temp.getX() + "" + temp.getY());
	}
	
	/**
	 * 这种方式虽然解决了++和对于约束条件的线程安全问题,但是有可能一个线程存的不是自己所产生的${@linkplain Pair}在他的载体PairManager是单例的情况下
	 * 这个方法相当于先写读，increment 写 store 读
	 */
	public void increment1() {
		synchronized (this) {
			p.incrementX();
			p.incrementY();
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		store(p);
		System.out.println(getPair().getX() + "" + getPair().getY());
		
	}
	
	/**
	 * 测试increment 和 increment1 的线程安全
	 * @param args
	 */
	public static void main(String[] args) {
		PairManager singleton = new PairManager2();
		PairManipulator manipulator = new PairManipulator(singleton);
		PairManipulator manipulator2 = new PairManipulator(singleton);
		Thread t1 = new Thread(manipulator);
		Thread t2 = new Thread(manipulator2);
		t1.start();
		t2.start();
	}
}
