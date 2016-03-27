package com.cjs.类加载.类加载过程;

/**
 * 	 类从被加载到虚拟机内存开始，直到卸出内存为止，整个生命周期包括了：<br/>
 * 加载、连接（验证、准备、解析）、初始化、使用和卸载这5个阶段。
 * 
 * --------------------------------------------------
 * 
 * 初始化的时机：
 * 	1、调用Java命令来执行一个类的main方法时
 * 	2、使用反射（eg：Class.forName("com.cjs.test.Test")）、反序列化、克隆来生成对象时
 * 	3、使用new关键字来new一个对象
 * 	4、访问一个类的静态变量（除了访问final修饰的常量）和静态方法时
 * 	5、当初始化一个类时，先初始化其父类
 * 
 * 	当且仅当以上5种情况（主动引用）才会使类进行初始化
 * 
 * 	被动引用的例子：
 * 	1、<code>访问从父类继承的static变量</code>
 * 	2、String[] stringArray = new String[10];  动态初始化  指定数组的大小，然后虚拟机给他们附上默认值null
 * 	3、<code>访问final修饰的常量</code>
 * 
 * 	------------------------------------------------
 * 
 * 
 * @author 陈景帅
 *
 */
public class 类加载过程的第一个例子 {
	public static void main(String[] args) {
		System.out.println("count1 = " + SingleTon.count1);
		System.out.println("count2 = " + SingleTon.count2);
	}
}

class SingleTon{
	private static SingleTon singleTon = new SingleTon();
	public static int count1;
	public static int count2 = 0;

	private SingleTon() {
		count1++;
		count2++;
	}

	public static SingleTon getInstance() {
		return singleTon;
	}
}