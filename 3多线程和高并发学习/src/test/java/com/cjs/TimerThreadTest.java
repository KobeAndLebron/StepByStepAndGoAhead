package com.cjs;

public class TimerThreadTest {
	
	public static void main(String[] args) {
		TimerThread test = new TimerThread(5000);
		test.start();
	}
}
