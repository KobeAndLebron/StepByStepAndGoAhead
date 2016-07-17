package com.cjs.synchronizedBlockVsMethod;

/**
 * 检查者进程-当{@linkplain PairManager}对Pair进行操作后判断{@link Pair}是否符合规范-{@linkplain Pair#checkState()}
 * 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月22日-下午9:37:00
 */
public class PairChecker implements Runnable{
	private PairManager pm;
	
	public PairChecker(PairManager pm){
		this.pm = pm;
	}
	public void run() {
		while(true){
			pm.at.incrementAndGet();
			pm.getPair().checkState();
		}
	}
	
}
