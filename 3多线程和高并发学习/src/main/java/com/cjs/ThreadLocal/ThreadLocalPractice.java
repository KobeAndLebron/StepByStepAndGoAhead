package com.cjs.ThreadLocal;

/**
 * 	线程局部变量，每个线程都为该线程局部变量保存相对应的值-具体保存在{@linkplain ThreadLocal}的内部类ThreadLocalMap里的Entry[]数组里面,
 * 数组的索引由对应的ThreadLocal对象里的threadLocalHashCode映射所得到（具体生成策略见源码），所以不会发生冲突（具体结合有道云笔记来学习）
 * 
 * ********Benefit:将变量的作用域变为线程所有，不是多个线程共享。
 * 
 * 原理:
 * ThreadLocal里面get方法：
 * 	1、先获取当前线程-Thread.currentThread();
 * 	2、获取当前线程的ThreadLocal.ThreadLocalMap，这个变量完全由ThreadLocal创建和管理，但是引用交给Thread管理。
 * 		如果map为空(则创建map)或者此ThreadLocal对象对应的Entry对象为null，就调用initialValue方法（这个方法使用的是模版方法模式，
 * 可以由子类重写改变其行为，默认返回null），然后把这个初始值放入到map的entry数组中;
 * 	ThreadLocal values pertaining to this thread. This map is maintained
 * 		by the ThreadLocal class. 
 *  ThreadLocal.ThreadLocalMap threadLocals = null;
 *  3、根据当前的线程局部变量(ThreadLocal的threadLocalHashCode)和Entry数组长度(index = key.threadLocalHashCode & 
 *  	(table.length - 1))来获取线程局部变量所对应的值
 *  
 *  set相同
 *  特殊的是第二步-不会调用initialValue方法，如果map为空的话，则根据当前线程和set的值还有当前ThreadLocal来设置/创建map
 * 
 * 	Struts2的ActionContext就是用的这种方法，此类的Integer相当于ActionContext，
 *  用这种方法就可以达到每个线程都有自己独立的ActionContext
 * @author 陈景帅
 *
 */
public class ThreadLocalPractice {
	
	final static int initialInteger = 1;
	private static final ThreadLocal<Integer> integerThreadLocal = new ThreadLocal<Integer>(){
		@Override
		protected Integer initialValue() {
			return initialInteger;
		}
	};
	
	public static void main(String[] args) {
		System.out.println(ThreadLocalPractice.getContext());
		ThreadLocalPractice.setContext(new Integer(3));
		Thread1 t1 = new Thread1();
		Thread2 t2 = new Thread2();
		t1.start();
		t2.start();
		System.out.println(ThreadLocalPractice.getContext());
	}
	
	public static void setContext(Integer value){
		integerThreadLocal.set(value);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getContext(){
		return (T) integerThreadLocal.get();
	}
}

class Thread1 extends Thread{
	public void run(){
		ThreadLocalPractice.setContext(new Integer(5));
		System.out.println(ThreadLocalPractice.getContext());
	}
}


class Thread2 extends Thread{
	public void run(){
		ThreadLocalPractice.setContext(new Integer(4));
		System.out.println(ThreadLocalPractice.getContext());
	}
}