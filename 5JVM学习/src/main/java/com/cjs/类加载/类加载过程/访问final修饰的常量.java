package com.cjs.类加载.类加载过程;

public class 访问final修饰的常量 {
	public static void main(String[] args) {
		System.out.println(Father1.str);
		System.out.println(Father2.str);
	}
}

class Father1{
	static final String str = "aaa" + "bbb";
	
	static{
		System.out.println(Father1.class.getSimpleName() + "被初始化");
	}
}

class Father2{
	static final String str = "aaa" + getCCC();
	
	static{
		System.out.println(Father2.class.getSimpleName() + "被初始化");
	}

	private static String getCCC() {
		return "ccc";
	}
}