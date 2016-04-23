package com.cjs.notSafeThreadExamples.third_atomicity;

/**
 * Reuses storage so we do not run out of memory.
 * 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月16日-下午8:57:59
 */
public class CircularSet {
	// This array is used for integer storage
	private int[] array;
	// This array's length
	private int len;
	// The key of reusing storage
	private int index = 0;
	
	public CircularSet(int size){
		array = new int[size];
		len = size;
		// Initialize to a value not produced by the SerialNumberGenerator
		for(int i = 0; i < size; i++){
			array[i] = -1;
		}
	}
	
	/**
	 *   add为写,contains为读,array的载体CircularSet为单例模式在内存因此不用管他的载体(${@linkplain SerialNumberChecker})是不是单例,
	 *  所以需要考虑线程安全在读contain和写add交叉执行的执行下/写add的交叉执行(由于线程的并发性和异步性)+++++++
	 *
	 * 单例给方法加上Synchronize的关键字即可
	 * 
	 * @param i added number
	 */
	public synchronized void add(int i){
		array[index] = i;
		// Wrap index and write over old elements:
		index = ++index % len;
	}
	
	public synchronized boolean contains(int val){
		for(int i = 0; i < len; i++){
			if(array[i] == val){
				return true;
			}
		}
		return false;
	}
}
