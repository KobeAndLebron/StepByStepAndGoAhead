package com.cjs.类加载.类加载过程;

public class 访问从父类继承的static变量 {
	static int i = 1;
	
	static{
		System.out.println("父类初始化");
	}
	public static void main(String[] args) {
		System.out.println(Son.i);
		System.out.println(System.getProperty("java.class.path"));
	}
}

class Son extends 访问从父类继承的static变量{
	static{
		System.out.println("子类初始化");
	}
}