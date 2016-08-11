package com.cjs.notSafeThreadExamples.first;

public abstract class IntGenerator {
	/**
	 * volatile 的作用？？？
	 */
	private volatile boolean canceled = false;
	public abstract int next();
	public void cancel(){
		this.canceled = true;
	}
	
	public boolean isCanceled(){
		return this.canceled;
	}
}
