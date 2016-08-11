package com.cjs.ThreadLocal;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 为每一个线 程分配ID
 * 将对象的作用域变为线程所有的,而不是普通的范围：方法/类/对象
 * 
 * @author ChenJingShuai
 *
 */
public class ThreadId {
	private static final AtomicInteger nextId = new AtomicInteger();
	
	// Thread local variable containing each thread's ID
	private static final ThreadLocal<Integer> threadId = new ThreadLocal<Integer>(){
		public Integer initialValue(){
			// return 0; // try it can understand that initialValue method of ThreadLocal
			return nextId.getAndIncrement();
		}
	};
	
	// Return the current thread's unique ID , assigning it if necessary
	public static Integer get(){
		System.out.println(Thread.currentThread().getName());
		return threadId.get();
	}
	
	public static void main(String[] args) {
		Thread thread1 = new Thread(){
			public void run(){
				Integer d = ThreadId.get();
				System.out.println(Thread.currentThread().getName() + " " + d);
			}
		};
		Thread thread2 = new Thread(){
			public void run(){
				Integer d = ThreadId.get();
				System.out.println(Thread.currentThread().getName() + " " + d);
			}
		};
		thread1.start();
		thread2.start();
		System.out.println(Thread.currentThread().getName() + " " + ThreadId.get());
	}
}
