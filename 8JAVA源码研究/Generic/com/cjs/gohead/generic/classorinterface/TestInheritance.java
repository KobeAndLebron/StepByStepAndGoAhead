package com.cjs.gohead.generic.classorinterface;

public class TestInheritance {

}

class Father{
	public Number test1(){
		return null;
	}
}

class Son extends Father{
	public Integer test1(){
		return null;
	}
	
	// 编译器会自动生成一个桥方法用于支持类型协变的问题（使用泛型类或者泛型接口的时候也会遇到此问题-由于擦除）
	// 当使用泛型类或泛型接口的时候，当被继承（实现）的方法含有方法参数的时候，也会使用到编译器所生成的桥方法
	/**
	 * 	// Method descriptor #16 ()Ljava/lang/Number;
  		// Stack: 1, Locals: 1
  	public bridge synthetic java.lang.Number test1();
	    0  aload_0 [this]
	    1  invokevirtual com.cjs.gohead.generic.classorinterface.Son.test1() : java.lang.Integer [17]
	    4  areturn
	      Line numbers:
	        [pc: 0, line: 1]
	 */
}