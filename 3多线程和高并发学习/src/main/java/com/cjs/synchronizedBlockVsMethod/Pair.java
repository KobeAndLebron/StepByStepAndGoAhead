package com.cjs.synchronizedBlockVsMethod;

/**
 *  Synchronizing blocks instead of entire methods.Also demonstrates protection of a non-thread-safe class with 
 * a thread-safe one.
 * 
 * 此类的线程安全安全问题：+++++++
 * 	1、x和y的载体在内存中是单例-不用管使用它的类(归根到底为Thread/Runnable)是否是单例
 *  2、++的非原子性,还有对于{@linkplain #checkState()}的约束条件来说存在读写交叉问题:incermentX和incermentY必须同时被一个线程(任务)执行完毕
 *  
 *  他的线程安全问题在{@linkplain PairManager#increment()}得到解决，即protect a non-thread-safe class with a thread-safe one.
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月22日-下午8:44:43
 */
public class Pair { // Not thread-safe
	private int x;
	private int y;
	public Pair(int x2, int y2) {
		this.x = x2;
		this.y = y2;
	}

	public Pair() {
	}

	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public void incrementX(){
		x++;
	}
	
	public void incrementY(){
		y++;
	}
	
	public String toString(){
		return "x: " + x + ", y: " + y; 
	}
	
	// Arbitrary invariant -- both variables must be equal
	public void checkState(){
		if(x != y){
			System.exit(0);
		}
	}
}
