package com.cjs.wait_notify;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月6日-下午8:20:16
 */
public class ProducerAndCustomer {
	public static void main(String[] args) {
		int pNum = 10;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < pNum; i++) {
            ProduceThread pt = new ProduceThread("producer" + i, list);
            Thread t = new Thread(pt);
            t.start();
        }
        int cNum = 1;
        for (int i = 0; i < cNum; i++) {
            CustomerThread ct = new CustomerThread("customer" + i, list);
            Thread t = new Thread(ct);
            t.start();
        }
    }
}

class CustomerThread implements Runnable{
	private String name;
	private List<String> list;

    public CustomerThread(String name, List<String> list) {
        this.name = name;
        this.list = list;
    }

    public void run() {
        while (true) {
            // or use list as monitor.
            synchronized (Object.class) {
                while (list.size() == 0) {
                    System.out.println(this.name + " have run out all resource");
                    try {
                        Object.class.notifyAll();
                        Object.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(this.name + " is running out " + list.size() + " resource");
                list.remove(list.size() - 1);
                System.out.println(this.name + " has run out " + (list.size() + 1) + " resource");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
	
}

class ProduceThread implements Runnable{
	private String name;
	private List<String> list;
	
	public ProduceThread(String name ,List<String> list){
		this.name = name;
		this.list = list;
	}
	
	public void run() {
        while (true) {
            // Tread that will enter the monitor region must go through EntrySet,If no other threads
            // in the entry set or in the WaitSet,the current thread will enter the monitor region,
            // otherwise,it will compete with those threads.
            // if there is a thread in the monitor region,the current thread will be blocked(this
            // result have a proof at Test1.java)
            synchronized (Object.class) {
                while (list.size() == 10) {
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
            } // Exit the monitor region and release the monitor
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
	
}