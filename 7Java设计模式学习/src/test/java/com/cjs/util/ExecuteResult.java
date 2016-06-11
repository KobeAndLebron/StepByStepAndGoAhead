package com.cjs.util;

/**
 * 执行结果类,除了第一次使用之前,都必须调用init方法
 * @author ChenJingShuai  
 *
 * Make a bit of progress every day. 2016年5月22日-上午11:19:57
 *
 */
public class ExecuteResult {
	private static int executedThreadNum = 0;
	private static boolean flag = true;
	
	public static void init(){
		executedThreadNum = 0;
		flag = true;
	}
	
	public static void addExecutedThreadNum(int i){
		executedThreadNum = executedThreadNum + i;
	}
	
	/**
	 * 线程是否并发执行完毕
	 * @param trueThreadNum 真正的线程数
	 * @return
	 */
	public static boolean isFinish(int trueThreadNum){
		return executedThreadNum == trueThreadNum;
	}
	
	public static int getNum(){
		return executedThreadNum;
	}
	
	public static void setFalseResult(){
		flag = false;
	}
	
	public static boolean ifSuccess(){
		return flag;
	}
}
