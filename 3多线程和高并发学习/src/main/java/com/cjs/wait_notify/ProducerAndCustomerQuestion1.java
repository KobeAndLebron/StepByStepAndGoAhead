package com.cjs.wait_notify;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 生产者消费者问题1：
 * 	所有的被唤醒的线程应该把代表唤醒的信号当作是一种它可以使用共享数据的暗示！！！所以应该使用while来代替if
 * 	
 * 	English explanation: Notice:
 * 	The kind of monitor(Cooperation) used in the Java virtual machine is sometimes called a Signal and Continue monitor
 * because <b>after a thread does a notify (the signal) it retains ownership of the monitor and continues executing
 * the monitor region (the continue)</b>. At some later time, the notifying thread releases the monitor and a waiting 
 * thread is resurrected. Presumably, the waiting thread suspended itself because the data protected by the monitor 
 * wasn't in a state that would allow the thread to continue doing useful work. Also, the notifying thread presumably 
 * executed the notify command after it had placed the data protected by the monitor into the state desired by the 
 * waiting thread. But because the notifying thread continued, it may have altered the state after the notify such 
 * that the waiting thread still can't do useful work. Alternatively, a third thread may have acquired the monitor 
 * after the notifying thread released it but before the waiting thread acquired it, and the third thread may have 
 * changed the state of the protected data. As a result, a notify must often be considered by waiting threads merely 
 * as a hint that the desired state may exist. Each time a waiting thread is resurrected, it may need to check the
 * state again to determine whether it can move forward and do useful work. If it finds the data still isn't in the 
 * desired state, the thread could execute another wait or give up and exit the monitor. 
 * 	so we should use while instead of if~~~
 * 
 * 	As an example, consider the scenario described above that involves a buffer once again , a read thread, and a write
 * thread. Assume the buffer is protected by a monitor. When a read thread enters the monitor that protects the buffer, 
 * it checks to see if the buffer is empty. If the buffer is not empty, the read thread reads (and removes) some data 
 * from the buffer. Satisfied, it exits the monitor. On the other hand, if the buffer is empty, the read thread executes
 * a wait command. As soon as it executes the wait, the read thread is suspended and placed into the monitor's wait set.
 * In the process, the read thread releases the monitor, which becomes available to other threads. At some later time, 
 * the write thread enters the monitor, writes some data into the buffer, executes a notify, and exits the monitor. 
 * When the write thread executes the notify, the read thread is marked for eventual resurrection. After the write 
 * thread has exited the monitor, the read thread is resurrected as the owner of the monitor. If there is any chance 
 * that some other thread has come along and consumed the data left by the write thread, the read thread must explicitly
 * check to make sure the buffer is not empty. If there is no chance that any other thread has consumed the data, 
 * then the read thread can just assume the data exists. The read thread reads some data from the buffer and exits 
 * the monitor.
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月6日-下午8:20:16
 */
public class ProducerAndCustomerQuestion1 {
	public static void main(String[] args) {
		int pNum = 10;
		List<String> list = new ArrayList<String>();
		for(int i = 0;i < pNum;i++){
			ProduceThread3 pt = new ProduceThread3("producer" + i, list);
			Thread t = new Thread(pt);
			t.start();
		}
		int cNum = 100;
		for(int i = 0;i < cNum;i++){
			CustomerThread3 ct = new CustomerThread3("customer" + i, list);
			Thread t = new Thread(ct);
			t.start();
		}
	}
}

class CustomerThread3 implements Runnable{
	private String name;
	private List<String> list;
	
	public CustomerThread3(String name ,List<String> list){
		this.name = name;
		this.list = list;
	}
	
	public void run() {
		while(true){
			synchronized (Object.class) {
				while(list.size() <= 0){
					System.out.println(this.name + " have runed out all resource");
					try {
						Object.class.notifyAll();
						Object.class.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println(this.name + " is running out " + list.size() + " resource");
				list.remove(list.size() - 1);
				System.out.println(this.name + " has runed out " + (list.size() + 1) + " resource");
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}

class ProduceThread3 implements Runnable{
	private String name;
	private List<String> list;
	
	public ProduceThread3(String name ,List<String> list){
		this.name = name;
		this.list = list;
	}
	
	public void run() {
		while(true){
			// Tread that will enter the monitor region must go through EntrySet,If no other threads 
			// in the entry set or in the WaitSet,the current thread will enter the monitor region,
			// otherwise,it will compete with those threads.
			// if there is a thread in the monitor region,the current thread will be blocked(this 
			// result have a proof at Test1.java)
			synchronized (Object.class) {
				while(list.size() >= 10){
					System.out.println(this.name + " have produce what list can load");
					try {
						/**
						 * In JVM,every object and class(default for static method) 
						 * is logically associated with monitor/lock!!!
						 * 
						 * this.wait()将抛出IllegalMonitorStateException，因为当前线程对象持有的不是自己的monitor，
						 * 而拿的是Object.class对象的monitor，所以应该执行Object.class.wait()来释放Object.class的monitor
						 * 
						 */
						Object.class.notifyAll();
						Object.class.wait();
						// enter into WaitSet-Be blocked and waiting to be notified by the other thread
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println(this.name + " is producing " + (list.size() + 1) + " resource");
				list.add("resource" + list.size());
				System.out.println(this.name + " has produced " + list.size() + " resource");
			}// Exit the monitor region and release the monitor
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}