package com.cjs.单例模式;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class TestSingeton {
	private static Class<?> clazz = null;
	public static void main(String[] args) {
		System.out.println("请输入你要测试的单例类的编号: ");
		Scanner in = new Scanner(System.in);
		
		String number = null;
		while((number = in.nextLine()) == "\\n"){
		}
		
		if(in != null){
			in.close();
		}
			
		Object obj = getSingeton(number);
		
		for(int i = 0; i < 100; i++){
			Object tempObj = getSingeton(number);
			if(obj != tempObj){
				System.out.println("单例不正确");
			}
		}
		System.out.println("单例正确");
	}
	
	public static Object getSingeton(String number){
		try{
			if(clazz == null){
				clazz = Class.forName("com.cjs.单例模式.Singeton" + number);
			}
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		
		Object obj = null;
		Method method = null;
		
		try{
			method = clazz.getMethod("getSingeton", new Class<?>[]{});
			obj	= method.invoke(clazz, new Object[]{});
		}catch(NoSuchMethodException e){
			e.printStackTrace();
		}catch(IllegalAccessException e){
			e.printStackTrace();
		}catch(IllegalArgumentException e){
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return obj;
	}
}
