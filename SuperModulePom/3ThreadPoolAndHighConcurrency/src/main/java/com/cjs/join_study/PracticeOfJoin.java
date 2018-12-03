package com.cjs.join_study;


/**
 * Control the order of execution of threads by signal
 * 
 * @author ChenJingShuai
 *
 */
public class PracticeOfJoin {
	private static int signal = 0;
	public static void main(String[] args) throws InterruptedException {
		theFirstWayOfControl(300);
	}

	private static void theFirstWayOfControl(int threadNum) throws InterruptedException {
		for(int i = 0; i < threadNum ; i++){
			Runnable runnable = new TestRunnable();
			Thread tempThread = new Thread(runnable);
			synchronized (PracticeOfJoin.class) { // 每启动一个线程，将信号量+1
				signal++;
			}
			tempThread.start();
		}
		/**
		 *    当前线程必须拥有此对象（调用wait的对象）的monitor才能调用wait方法否则抛出IllegalMonitorStateException
		 *  然后等待其他线程调用此对象的notify方法-此处利用了一个线程在结束的时候会调用他的notifyAll方法这个特性
		 */
		/*synchronized (thread) {
			thread.wait();
		}*/
		
		while(signal != 0){
			Thread.sleep(10);
			/*System.out.println("I will wait util all of threads started by me is not over");*/
		}
		System.out.println("Everything is in control and they are all dead.^_^");
		System.out.println("signal now is " + signal);
	}
	
	public static void theSecondWayOfControl() throws InterruptedException {
		int threadNum = 10;
		Thread thread = null;
		for(int i = 0; i < threadNum ; i++){
			Runnable runnable = new TestRunnable();
			Thread tempThread = new Thread(runnable);
			if(i == threadNum - 1){
				thread = tempThread;
			}
			tempThread.start();
		}
		/**
		 *  而join的使用不用获取monitor，虽然他的原理也是wait，但是他在join方法里面会获取monitor然后进行wait
		 */
		thread.join();
		System.out.println("Everything is in control and they are all dead.^_^");
	}
	
	static class TestRunnable implements Runnable{
		public void run() {
			try {
				Thread.sleep(1000);
				System.out.println(Thread.currentThread().getName() + "say : Fuck,I have finished");
				synchronized (PracticeOfJoin.class) {
					signal--;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}
