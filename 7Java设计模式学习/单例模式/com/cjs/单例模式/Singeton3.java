package com.cjs.单例模式;

import java.util.Arrays;

/**
 * 懒汉式单例模式
 *
 * @author 陈景帅
 *
 * 每天进步一点——2016年2月26日
 *
 */
public class Singeton3 {
	private Singeton3(){     
	    
    }     
    
    private static Singeton3 instance;
    
    // 非线程安全
    public static Singeton3 getInstance(){     
        if(instance == null){ 
        	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        	return instance = new Singeton3();     
        }else{     
            return instance;     
        }  
    }
    
    // 线程安全但是效率低下
    public static synchronized Singeton3 getInstance1(){     
        if(instance == null){     
        	return instance = new Singeton3();     
        }else{     
            return instance;     
        }  
    }
    
    // 通过双检索机制来提高效率
    public static Singeton3 getInstance2(){     
        if(instance == null){ 
        	synchronized(Singeton3.class){
        		if(instance == null){ 
        			return instance = new Singeton3();
        		}
        	}
        }
        
        return instance;     
    } 
    public String toString(){
    	return this.hashCode() + this.getClass().getName();
    }
    
    public static void init(){
    	instance = null;
    }
}


class SingletonChecker{
	private static final int LENGTH = 1000;
	private Singeton3[] array = new Singeton3[LENGTH];
	private int index = 0;
	
	public SingletonChecker(){
	}
	
	/**
	 * 线程安全的方法,同一时刻只能有一个任务运行在相同对象的方法/临界区内
	 * 
	 * @param s
	 * @return
	 */
	public synchronized boolean check(Singeton3 s){
		boolean flag = false;
		if(array[0] == null){
			flag = true;
		}else{
			if(s == array[index - 1]){
				flag = true;
			}else{
				flag = false;
			}
		}
		array[index] = s;
		index = ++index % LENGTH;
		return flag;
	}
	
	/**
	 * 1、array是成员变量,位于堆内存
	 * 2、他的载体类{@linkplain SingletonChecker}使用也是单例的
	 * 3、存在读写交叉问题-------
	 * 
	 * 测试步骤此方法: 1、注释到{@linkplain SingletonChecker#check(Singeton3)}方法
	 * 		    2、然后将此方法的名字的1去掉,
	 * 		    3、最后运行${@linkplain TestSingleton3}
	 * 
	 * @param s
	 * @return
	 */
	public boolean check1(Singeton3 s){
		boolean flag = false;
		if(array[0] == null){
			/**
			 * 如果去掉synchronized关键字并且让此线程睡眠-为了让多个线程都执行到array[0]==null,那么就会让此方法的上下文失效-------
			 */
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			flag = true;
		}else{
			if(s == array[index - 1]){
				flag = true;
			}else{
				flag = false;
			}
		}
		array[index] = s;
		/**
		 * 如果线程在此睡眠,那么就会导致多个s放在相同的index上-------
		 */
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		index = ++index % LENGTH;
		return flag;
	}
	public String toString(){
		return Arrays.toString(array);
	}
}
