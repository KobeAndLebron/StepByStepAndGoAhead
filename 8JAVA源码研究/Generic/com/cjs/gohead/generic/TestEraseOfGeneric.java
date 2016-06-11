package com.cjs.gohead.generic;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class TestEraseOfGeneric {
	public static void main(String[] args) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		ArrayList<? extends Integer> arrayList3=new ArrayList<>();
		arrayList3.getClass().getMethod("add", Object.class).invoke(arrayList3, "asd");
		for (int i=0;i<arrayList3.size();i++) {
			System.out.println(arrayList3.get(i));
		}
	}
}
