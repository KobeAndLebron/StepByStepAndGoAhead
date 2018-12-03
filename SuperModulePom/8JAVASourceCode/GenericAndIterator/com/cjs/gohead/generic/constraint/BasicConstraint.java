package com.cjs.gohead.generic.constraint;

import java.util.ArrayList;

/**
 * 
 * 泛型原理：只存在编译时期,用于做类型检查；运行时会被擦除,类型变量(T/K/V)会被替换限定类型(T extends Number),无限定类型用Object替换(T extends Object)
 * 作用：将运行时(Runtime)所需的检查移到编译时(Compile time),因为运行时find bugs比编译时要waste时间。This is the most important to remember for learning Generics.
 * 		避免了{@linkplain ClassCastException}的出现，这也是泛型出现的初衷，所有泛型的使用都不应该违背其初衷.
 * 	eg:List<Object>不是List<String>的父类,如果是的话,那么如果将后者赋给前者,还是不知道集合里面放的是什么类,违背了其初衷;另一方面由于泛型信息会被擦除在编译后,class文件中他俩
 * 都是List类;但是这样又限制了Java继承的特性,所以出现了通配符->? extends/super Object
 * 
 * 泛型的使用对象/场景:
 * 	1. 泛型类,例如使用最广泛的容器类,它持有一大堆对象的引用;
 * 	2. 泛型接口,类似于泛型类;涉及到桥方法(支持返回类型斜变和泛型擦除问题).
 *  3. 泛型方法.	可以根据返回类型或者给定参数来确定泛型方法的类型变量
 *  总之,无论是这三者哪一者的使用,都要在编译期确定其类型变量.
 * 
 * 泛型约束：
 * 	1. 不能将基本数据类型作为类型变量.
 * 	2. ArrayList<String> 与 ArrayList<Ineger>变量的class对象equal,因为type parameter会被擦除.
 *  3. 类型变量不能在静态变量声明处 静态代码块 静态方法处使用,如果可以使用那么{@linkplain BasicConstraint#t}代表什么类型呢???
 *  4. 不能创造一个类型变量的对象->new T().擦除后将会创造一个Object对象
 *     不能获取一个类型变量的class对象->T.class.擦除后会获取Object的class对象
 *     不能new一个类型变量的数组->new T[],擦除之后会new一个Object数组,但是可以使用T[] t引用;可以通过这种方式获取泛型数组:
 *     	Arrays.newInstance(a.getClass().getComponentType(), length) a代表T[] a的a
 *   5. 不能定义一个泛型数组,应该使用List<List<String>>来代替List<String>[]	************
 *   
 *   TODO
 *   6. 异常与泛型
 *   7. 运行时获取泛型的参数类型
 *  
 * @author ChenJingShuai 25 Jul 2016
 *
 */
public class BasicConstraint<K, V> {
	/**
	 * 1.
	 * List<double> list;
	 * @param <T>
	 */
	/**
	 * 3 Cannot make a static reference to the non-static type K
	 * private static K t;
 		static {
			K t;
		}
	
		public static void t(){
			K k;
		}
	 */
	
	/********************5****************************/
	ArrayList<?>[] test = new ArrayList<?>[10];
	@SuppressWarnings("rawtypes")
	ArrayList[] test1 = new ArrayList[10];
	// Cannot create a generic array of ArrayList<String>
	// ArrayList<String>[] test2 = new ArrayList<String>[10];
	ArrayList<String>[] test3;
	/********************5****************************/
	
	public static <T> T get(){// 根据返回类型
		return null;
	}
	public static <T> T set(T t){// 根据给定参数来确定
		return null;
	}
}

