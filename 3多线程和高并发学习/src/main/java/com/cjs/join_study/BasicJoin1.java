package com.cjs.join_study;

public class BasicJoin1 {
	public static void main(String[] args) {
		Sleeper sleepy = new Sleeper("sleepy", 1500);
		Sleeper grumpy = new Sleeper("grumpy", 1500);
		Joiner dopey = new Joiner("dopey", sleepy);
		Joiner doc = new Joiner("grumpy", grumpy);
		grumpy.interrupt();
	}
}

class Joiner extends Thread{
	private Sleeper sleeper;
	
	public Joiner(String name ,Sleeper sleeper){
		super(name);
		this.sleeper = sleeper;
		start();
	}
	
	public void run(){
		try{
			sleeper.join();
		}catch(InterruptedException e){
			System.out.println(getName() + "was interrupted. ");
		}
		System.out.println(getName() + " join completed");
	}
}

class Sleeper extends Thread{
	private int duration;
	
	public Sleeper(String name ,int sleepTime){
		super(name);
		this.duration = sleepTime;
		start();
	}
	
	public void run(){
		try{
			Thread.sleep(duration);
		}catch(InterruptedException e){
			System.out.println(getName() + "was interrupted. " + "isInterrupted?" + isInterrupted());
		}
		System.out.println(getName() + " has awakened");
	}
	
}