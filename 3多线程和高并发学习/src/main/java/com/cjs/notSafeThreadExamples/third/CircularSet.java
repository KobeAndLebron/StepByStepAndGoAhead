package com.cjs.notSafeThreadExamples.third;

/**
 * Reuses storage so we do not run out of memory.
 * 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月16日-下午8:57:59
 */
public class CircularSet {
	private int[] array;
	private int len;
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
	 * add为写，contains为读，CircularSet为单例模式在内存，所以array也只有一份在内存，所以需要考虑线程安全
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
