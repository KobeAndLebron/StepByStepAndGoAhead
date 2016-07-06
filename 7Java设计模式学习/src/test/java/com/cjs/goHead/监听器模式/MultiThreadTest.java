package com.cjs.goHead.监听器模式;

import org.junit.Test;

public class MultiThreadTest {
	//create subject
	final MyTopic topic = new MyTopic();
			
	//create observers
	Observer obj1 = new MyObserver("Obj1");
	Observer obj2 = new MyObserver("Obj2");
	
	{
		//register observers to the subject
		topic.register(obj1);
		topic.register(obj2);
	
		
		//attach observer to subject
		obj1.setSubject(topic);
		obj2.setSubject(topic);
	}
	@Test
	public void testMultiThread(){
		Runnable run1 = new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("register new observer after sending message");
				Observer obj3 = new MyObserver("Obj3");
				obj3.setSubject(topic);
				topic.register(obj3);
			}
		};
		Runnable run2 = new Runnable() {
			@Override
			public void run() {
				System.out.println("Send Message firstly");
				//now send message to subject
				topic.postMessage("New Message");
			}
		};
		Thread thread1 = new Thread(run1);
		Thread thread2 = new Thread(run2);
		thread1.start();
		thread2.start();
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
