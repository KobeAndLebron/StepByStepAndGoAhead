package com.cjs.synchronizedBlockVsMethod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 相当于一个容器-有读和写方法
 * 保护Pair成线程安全的类
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月22日-下午9:31:40
 */
public abstract class PairManager {
	AtomicInteger at = new AtomicInteger(0);
	protected Pair p = new Pair();
	private List<Pair> storage = Collections.synchronizedList(new ArrayList<Pair>());
	
	// Assume this is a time consuming operation
	protected void store(Pair p){
		this.storage.add(p);
		try{
			Thread.sleep(100);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}

	public synchronized Pair getPair(){
		// Make a copy to variable called storage
		return new Pair(p.getX(), p.getY());
	}
	
	/**
	 * @see {@linkplain Pair}
	 */
	public abstract void increment();
}
