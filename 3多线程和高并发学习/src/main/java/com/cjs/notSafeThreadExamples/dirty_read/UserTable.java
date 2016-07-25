package com.cjs.notSafeThreadExamples.dirty_read;

public class UserTable {
	private String userName;
	private String userPwd;
	private static final Object WRITE_MONITOR = new Object();
	
	public UserTable() {
		super();
	}

	public UserTable(String userName, String userPwd) {
		super();
		this.userName = userName;
		this.userPwd = userPwd;
	}
	
	/**
	 * Synchronized 防止了多个ModifyThread同时对UserTable进行修改
	 * @param userName  
	 * @param userPwd
	 */
	public void updateUser(String userName, String userPwd){
		synchronized (WRITE_MONITOR) {
			this.userName = userName;
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.userPwd = userPwd;
		}
	}
	
	/**
	 * 防止了在写的过程对UserTable进行读：即脏读问题
	 */
	public String toString(){
		try{
			WRITE_MONITOR.notify();
			System.out.println("Add Write Lock");
			synchronized (WRITE_MONITOR) {
				return "UserName: " + this.userName + ", UserPassword: " + this.userPwd;
			}
		}catch (IllegalMonitorStateException e){
			// 有异常说明当前数据库没有写锁,可以进行读
			System.out.println("Concurrently Read");
			return "UserName: " + this.userName + ", UserPassword: " + this.userPwd;
		}
	}
}
