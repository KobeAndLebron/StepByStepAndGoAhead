package com.cjs.ThreadLocal;

/**
 * 	线程局部变量，每个线程都为该线程局部变量保存相对应的值. 将变量的作用域变为线程内所有，不是多个线程共享.
 *  使用场景: 全局传参, 比如分页参数的传递, Struts2的ActionContext的设置.
 *
 * 	实现方式: Thread里面有一个ThreadLocalMap的引用, 每一个ThreadLocal变量对应的
 * 	值就会放在这个ThreadLocalMap({@linkplain ThreadLocal#ThreadLocalMap}里. 多线程运行下每个线程操作自己的TLM.
 *  对应图片: https://github.com/KobeAndLebron/YoudaoNoteFileStorage/blob/master/concurrent/thread_local.png
 *
 * 原理:
 * ThreadLocal里面get方法：
 * 	1、先获取当前线程-Thread.currentThread();
 * 	2、获取当前线程的ThreadLocal.ThreadLocalMap，这个变量完全由ThreadLocal创建和操作，但是引用交给Thread管理。
 * 		如果map为空(则创建map)或者此ThreadLocal对象对应的Entry对象为null，就调用initialValue方法（这个方法使用的是模版方法模式，
 * 可以由子类重写改变其行为，默认返回null），然后把这个初始值放入到ThreadLocalMap的Entry数组中;
 * 	ThreadLocal values pertaining to this thread. This map is maintained
 * 		by the ThreadLocal class.
 *  ThreadLocal.ThreadLocalMap threadLocals = null;
 *  3、根据当前的线程局部变量(ThreadLocal的threadLocalHashCode)和Entry数组长度(index = key.threadLocalHashCode &
 *  	(table.length - 1))来获取此线程局部变量在当前线程的ThreadLocal.ThreadLocalGroup.Entry[]数组的索引
 *
 * 	缺点:
 * 	    使用不当容易造成内存泄漏, 在线程池的运行环境下, 假设有100个常驻线程, 现在有一个ThreadLocal变量, 那么每个线程都会有一个ThreadLocalMap
 * 	    的来存这个TL变量, 100线程如果运行完, 由于这些线程是常驻线程, 所以一直不会被垃圾回收, 但是程序已经不会再使用它,  此时就造成了内存泄漏的情况.
 *
 * 	    ThreadLocalMap中Entry持有对ThreadLocal变量的弱引用, 目的: 减少内存泄漏的可能性.
 * 	    当把ThreadLocal变量置为null的时候, 如果是弱引用的话, GC就会把这个ThreadLocal变量回收掉, 如果使用强引用
 * 	    的话, 会导致这个ThreadLocal变量一直不会被回收, 造成内存泄漏的问题.
 *
 * 	正确做法: 使用完ThreadLocal变量后及时Remove.
 *
 * @author 陈景帅
 *
 */
public class ThreadLocalPractice {
	private static final int initialInteger = 1;

	private static final ThreadLocal<Integer> integerThreadLocal = new ThreadLocal<Integer>(){
		@Override
		protected Integer initialValue() {
		    integerThreadLocal.remove();
			return initialInteger;
		}
	};

	public static void main(String[] args) {
		System.out.println(ThreadLocalPractice.getContext().toString());
		ThreadLocalPractice.setContext(3);
		Thread1 t1 = new Thread1();
		Thread2 t2 = new Thread2();
		t1.start();
		t2.start();
		System.out.println(ThreadLocalPractice.getContext().toString());
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
		ThreadLocalPractice.setContext(5);
		/**
		 * 去掉上面这句话,调用get方法就会调用initialValue方法
		 */
		System.out.println(ThreadLocalPractice.getContext().toString());
	}
}


class Thread2 extends Thread{
	public void run(){
		ThreadLocalPractice.setContext(4);
		System.out.println(ThreadLocalPractice.getContext().toString());
	}
}