package com.cjs.sychronizedkeyword;

/**
 * Test if state for threads in Entry Set and not in the monitor region monitored by object/class is blocked?
 * 
 * The answer is yes.and you can also look {@linkplain com.cjs.wait_notify.ProducerAndCustomer }  and ExplanationForConceptOfMonitor.jpg
 * ,there are some explanation about Entry Set,monitor.
 * 
 * Thread state for a thread blocked waiting for a monitor lock.
 * A thread in the blocked state is waiting for a monitor lock
 * to enter a synchronized block/method or
 * reenter a synchronized block/method after calling
 * {@link Object#wait() Object.wait}.
 *	BLOCKED,
 *	
 * You can run this application to understand the several states of thread-{@linkplain java.lang.Thread$State}.
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月6日-下午5:14:47
 */
public class ThreadStateForThreadsInEntrySet extends Thread{
	public ThreadStateForThreadsInEntrySet(String name){
		super(name);
	}
	
	public void run(){
		if(currentThread().getName().equals("Firstly Enter")){
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// 这种方式起不到互斥的作用
		/*synchronized(this){
			System.out.println(Thread.currentThread().getName() + " is monitoring by this");
			long sleepMills = 10000;
			System.out.println(getName() + " will sleep " + sleepMills);
			try {
				Thread.sleep(sleepMills);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(getName() + " has waken");
		}*/
		
		synchronized(ThreadStateForThreadsInEntrySet.class){
			System.out.println(Thread.currentThread().getName() + " is monitoring by Test.class Object");
			long sleepMills = 10;
			System.out.println(getName() + " will sleep " + sleepMills);
			try {
				// sleep不释放锁
				Thread.sleep(sleepMills);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				ThreadStateForThreadsInEntrySet.class.wait(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(getName() + " has waken");
		}
	}
	
	public static void main(String[] args) {
		Thread t1 = new ThreadStateForThreadsInEntrySet("Firstly Enter");
		Thread t2 = new ThreadStateForThreadsInEntrySet("Secnodly Enter");
		t1.start();
		t2.start();
		while(true){
			System.out.println(t2.getName() + " :  " + t2.getState());
			System.out.println(t1.getName() + " :  " + t1.getState());
		}
	}
	
}
