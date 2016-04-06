package com.cjs.sychronizedkeyword;

/**
 * Test if state for threads in Entry Set and not in the monitor region monitored by object/class are blocked?
 * 
 * The answer is yes.and you can also look {@linkplain com.cjs.wait_notify.ProducerAndCustomer },there are 
 * some explanation about Entry Set,monitor and so on
 * 
 * 
 * Thread state for a thread blocked waiting for a monitor lock.
 * A thread in the blocked state is waiting for a monitor lock
 * to enter a synchronized block/method or
 * reenter a synchronized block/method after calling
 * {@link Object#wait() Object.wait}.
	BLOCKED,

 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月6日-下午5:14:47
 */
public class Test1 extends Thread{
	public Test1(String name){
		super(name);
	}
	
	public void run(){
		if(currentThread().getName().equals("Be Blocked")){
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
		
		synchronized(Test1.class){
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
				Test1.class.wait(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(getName() + " has waken");
		}
	}
	
	public static void main(String[] args) {
		Thread t1 = new Test1("Firstly Enter");
		Thread t2 = new Test1("Secnodly Enter");
		t1.start();
		t2.start();
		while(true){
			System.out.println(t2.getName() + " :  " + t2.getState());
			System.out.println(t1.getName() + " :  " + t1.getState());
		}
	}
	
}
