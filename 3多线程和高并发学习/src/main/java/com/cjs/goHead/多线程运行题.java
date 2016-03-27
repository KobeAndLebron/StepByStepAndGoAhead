package com.cjs.goHead;
public class 多线程运行题 {
	private static int index;
	
	class MyThread extends Thread{
		@Override
		public void run(){
			System.out.println("run" + index++);
		}
		
		@Override 
		public synchronized void  start(){
			super.start();
			System.out.println("start" + index);
		}
	}
	
	public static void main(String[] args) {
		MyThread myThread = new 多线程运行题().new MyThread();
		index++;
		myThread.start();
		index++;
		myThread.run();
	}
}

