package com.cjs.synchronizedBlockVsMethod;

/**
 * 操作{@link Pair}的进程
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月22日-下午9:37:00
 */
public class PairManipulator implements Runnable{
	private PairManager pm;
	
	public PairManipulator(PairManager pm){
		this.pm = pm;
	}
	public void run() {
		while(true){
			pm.increment();
		}
	}
	
	public String toString(){
		return "Pair: " + pm.getPair() + ", checkCounter = " + pm.at.get();
	}
}
