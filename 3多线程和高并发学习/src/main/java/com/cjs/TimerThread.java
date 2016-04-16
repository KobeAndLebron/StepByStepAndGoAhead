package com.cjs;

/**
 * 计时器
 * 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月12日-下午9:50:26
 */
public class TimerThread extends Thread{
	private long timer = 0;
	private boolean isFinish = false;
	
	public TimerThread(long timer){
		this.timer = timer;
	}
	
	public TimerThread(String name, long timer){
		super(name);
		this.timer = timer;
	}
	
	public void run(){
		System.out.println("计时开始：");
		// 将时间分隔开来降低精确度，因为分割的话会导致线程的切换次数增多，切换也是需要时间的
		// 即使直接进行sleep也会实际计时时间偏大，因为线程切换也是需要时间的-虽然非常短。。。。。
		/*while(left > 0){
			// 随机sleep 1~1000 ms
			long time = (long) (Math.random() * 1000) + 1;
			try {
				Thread.sleep(time);
				left = timer - time;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
		try {
			Thread.sleep(timer);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.isFinish = true;
		System.out.println("计时结束：" + timer + "ms");
	}
	
	/**
	 * 计时是否结束
	 * @return
	 */
	public boolean isFinished(){
		return this.isFinish;
	}
}
