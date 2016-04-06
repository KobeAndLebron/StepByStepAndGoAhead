package com.cjs.wait_notify;

import java.util.ArrayList;
import java.util.List;

/**
 * 死锁情况2：
 * 	生产者的临界条件一触碰，就进入Object class的wait set了，然后只剩生产者了。。。。生产满了就死锁了
 *	或者消费者的临界条件一触碰.......................
 *
 *	因为这种情况生产者和消费者没有sleep，所以导致了临界情况很快被触碰了
 *
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月6日-下午8:23:59
 */
public class PACDeadLockOne1 {
	public static void main(String[] args) {
		int pNum = 1;
		List<String> list = new ArrayList<String>();
		for(int i = 0;i < pNum;i++){
			ProduceThread2 pt = new ProduceThread2("producer" + i, list);
			Thread t = new Thread(pt);
			t.start();
		}
		int cNum = 1;
		for(int i = 0;i < cNum;i++){
			CustomerThread2 ct = new CustomerThread2("customer" + i, list);
			Thread t = new Thread(ct);
			t.start();
		}
	}
}

class CustomerThread2 implements Runnable{
	private String name;
	private List<String> list;
	
	public CustomerThread2(String name ,List<String> list){
		this.name = name;
		this.list = list;
	}
	
	public void run() {
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

class ProduceThread2 implements Runnable{
	private String name;
	private List<String> list;
	
	public ProduceThread2(String name ,List<String> list){
		this.name = name;
		this.list = list;
	}
	
	public void run() {
		while(true){
			synchronized (Object.class) {
				if(list.size() == 100){
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