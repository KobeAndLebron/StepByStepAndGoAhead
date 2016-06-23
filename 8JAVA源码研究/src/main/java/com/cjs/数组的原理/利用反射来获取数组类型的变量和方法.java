package com.cjs.数组的原理;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 通过反射，可以知道，数组类型继承自Object，里面有Object的所有public方法；没有任何变量，只有一个length隐式变量。
 *
 * @author 陈景帅
 *
 * 每天进步一点——2016年2月6日
 *
 */
public class 利用反射来获取数组类型的变量和方法 {
	public static void main(String[] args) {
		Object[] objArray = new Object[10];
		Class<?> objArrayClazz = objArray.getClass();
		
		System.out.println(objArray.length);
		
		Method[] methods = objArrayClazz.getMethods();
		Field[] fields = objArrayClazz.getFields();
		System.out.println("数组类型和其父类的public方法如下：");
		for(Method eleMethod : methods){
			System.out.println(eleMethod.toString());
		}
		
		System.out.println("数组类型和其父类的public变量如下：");
		for(Field field : fields){
			System.out.println(field.toString());
		}
		
		methods = objArrayClazz.getDeclaredMethods();
		fields = objArrayClazz.getDeclaredFields();
		
		System.out.println("数组类型的方法(不包括父类)如下：");
		for(Method eleMethod : methods){
			System.out.println(eleMethod.toString());
		}
		
		System.out.println("数组类型的变量(不包括父类)如下：");
		for(Field field : fields){
			System.out.println(field.toString());
		}
	}
}
