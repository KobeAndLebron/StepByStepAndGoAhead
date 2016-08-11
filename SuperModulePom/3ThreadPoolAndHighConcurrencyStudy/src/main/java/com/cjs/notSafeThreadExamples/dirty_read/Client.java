package com.cjs.notSafeThreadExamples.dirty_read;

public class Client {
	public static void main(String[] args) {
		UserTable userTable = new UserTable("init", "init");
		System.out.println("Init:" + userTable.toString());
		ModifyThread mr = new ModifyThread(userTable);
		ReadThread rr = new ReadThread(userTable);
		Thread mt = new Thread(mr);
		Thread mt1 = new Thread(rr);
		Thread rt = new Thread(rr);
		mt.start();
		rt.start();
		mt1.start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("End:" + userTable.toString());
	}
}

class ModifyThread implements Runnable{
	private UserTable userTable;
	public ModifyThread(UserTable userTable) {
		this.userTable = userTable;
	}
	@Override
	public void run() {
		this.userTable.updateUser("root", "111111");
	}
	
}

class ReadThread implements Runnable{
	private UserTable userTable;
	
	public ReadThread(UserTable userTable) {
		this.userTable = userTable;
	}
	@Override
	public void run() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(this.userTable.toString());
	}
	
}