package com.cjs.数组的原理;

public class 数组的继承关系 {
	public static void main(String[] args) {
		Object[] objArray = new Object[10];
		String[] stringArray = new String[10];
		
		objArray = stringArray;
		
		objArray[0] = "aaa";
		
		System.out.println(stringArray.getClass().getInterfaces()[0]);
		
		stringArray = (String[]) objArray;
		System.out.println(stringArray[0]);
	}
}
