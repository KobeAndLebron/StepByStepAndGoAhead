package com.cjs.单例模式;

/**
 * 单例模式的第一种写法:饿汉式
 *	缺点：无论我们是否调用单例类的静态方法，在内存中都会存在有它的唯一实例
 *	优点：没有懒汉式的多线程问题
 * @author 陈景帅
 *
 * 每天进步一点——2016年2月24日
 *
 */
public class Singeton1 {
	private static Singeton1 singeton = new Singeton1();
	
	private Singeton1(){
		
	}
	
	public static Singeton1 getSingeton(){
		return Singeton1.singeton;
	}
	
}
