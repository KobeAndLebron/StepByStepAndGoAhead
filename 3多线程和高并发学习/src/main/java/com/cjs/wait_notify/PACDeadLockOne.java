package com.cjs.wait_notify;

import java.util.ArrayList;
import java.util.List;

/**
 *  死锁情况一：
 * 先让消费者睡眠
 * 然后让生产者生产满，使得生产者都进入BLOCKED状态
 * 最后让消费者消费，消费完也进入BLOCKED状态
 * 
 * 最后导致所有的生产者和消费者都位于Object.class's monitor region之外的wait set，没有任何线程去notify/all他们。。。。。。
 * 
 * 或者让生产者先睡眠
 *
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月6日-下午8:23:59
 */
public class PACDeadLockOne {
	public static void main(String[] args) {
		int pNum = 1;
		List<String> list = new ArrayList<String>();
		for(int i = 0;i < pNum;i++){
			ProduceThread1 pt = new ProduceThread1("producer" + i, list);
			Thread t = new Thread(pt);
			t.start();
		}
		int cNum = 1;
		for(int i = 0;i < cNum;i++){
			CustomerThread1 ct = new CustomerThread1("customer" + i, list);
			Thread t = new Thread(ct);
			t.start();
		}
	}
}

class CustomerThread1 implements Runnable{
	private String name;
	private List<String> list;
	
	public CustomerThread1(String name ,List<String> list){
		this.name = name;
		this.list = list;
	}
	
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}// -------------------------------------this
		while(true){
			synchronized (Object.class) {
				if(list.size() == 0){
					System.out.println(this.name + " have runed out all resource");
					try {
						Object.class.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}else{
					System.out.println(this.name + " is running out " + list.size() + " resource");
					list.remove(list.size() - 1);
					System.out.println(this.name + " has runed out " + (list.size() + 1) + " resource");
				}
			}
		}
	}
	
}

class ProduceThread1 implements Runnable{
	private String name;
	private List<String> list;
	
	public ProduceThread1(String name ,List<String> list){
		this.name = name;
		this.list = list;
	}
	
	public void run() {
		/*try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/ //--------------------------------------------or this
		while(true){
			synchronized (Object.class) {
				if(list.size() == 10){
					System.out.println(this.name + " have produce what list can load");
					try {
						Object.class.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}else{
					System.out.println(this.name + " is producing " + (list.size() + 1) + " resource");
					list.add("resource" + list.size());
					System.out.println(this.name + " has produced " + list.size() + " resource");
				}
			}
		}
	}
	
}