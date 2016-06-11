package com.cjs.gohead.generic.classorinterface;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * 
 * Fibonacci产生器
 * 
 * @author ChenJingShuai  
 * 2016年5月21日
 *
 */
public class FibonacciGenerator implements Generator<Integer>, Iterable<Integer>{
	private int size = 0;
	
	public FibonacciGenerator(int size){
		this.size = size;
	}
	
	public Integer next() {
		return null;
	}

	private int computeFin(int n){
		if(n == 1){
			return 1;
		}else if(n > 1){
			return computeFin(n - 1) + computeFin(n - 2);
		}else{
			return 0;
		}
	}
	public Iterator<Integer> iterator() {
		return new FinIterator();
	}

	class FinIterator implements Iterator<Integer>{
		private int n = FibonacciGenerator.this.size;
		private final int JUDGE_THREAD_SAFE = n;
		
		@Override
		public boolean hasNext() {
			return n-- > 0;
		}

		@Override
		public Integer next() {
			if (JUDGE_THREAD_SAFE != FibonacciGenerator.this.size) {
 				throw new ConcurrentModificationException();
			}
			return FibonacciGenerator.this.computeFin(n);
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	private static final FibonacciGenerator fin = new FibonacciGenerator(10);
	public static void main(String[] args) {
		singleThread();
		multiThread();
	}

	private static void multiThread() {
		Runnable runnable = new Runnable() {
 			@Override
			public void run() {
 				try {
 					Thread.sleep(100);
 				} catch (InterruptedException e) {
 					e.printStackTrace();
 				}
				fin.size = 100;
				System.out.println("Modified");
			}
		};
		new Thread(runnable).start();
		for(Integer i : fin){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(i);
		}
	}

	private static void singleThread() {
		for(Integer i : fin){
			System.out.println(i);
		}
		fin.size = 20;
		for(Integer i : fin){
			System.out.println(i);
		}
	}
}
