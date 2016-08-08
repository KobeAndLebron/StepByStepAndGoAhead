package com.cjs.gohead.generic;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
 
/**
 *
 * @version 1.1
 * 
 * @author Chen
 * 
 * */
public class ObjectString {
	private static ArrayList<Class<?>> primitiveArray = new ArrayList<Class<?>>(8);
	static{
		primitiveArray.add(Byte.class);
		primitiveArray.add(Short.class);
		primitiveArray.add(Integer.class);
		primitiveArray.add(Long.class);
		primitiveArray.add(Character.class);
		primitiveArray.add(Boolean.class);
		primitiveArray.add(Float.class);
		primitiveArray.add(Double.class);
	}
	
	public static String toString(Object obj){
		if(obj == null){
			return "null";
		}
		Class<?> clazz = obj.getClass();
		
		if(clazz.isPrimitive() || clazz == String.class || clazz == char[].class || primitiveArray.contains(clazz)){
			return String.valueOf(obj);
		}
		if(clazz.isArray()){
			Object[] objArray = (Object[]) obj;
			
			StringBuilder sb = new StringBuilder();
			sb.append("[ ");
			for(Object element : objArray){
				sb.append(ObjectString.toString(element) + " , ");
			}
			String s = sb.substring(0, sb.lastIndexOf(",") - 1);
			return s + " ]";
		}
		/*if(isImplementIterable(clazz)){
			@SuppressWarnings("unchecked")
			Iterable<Object> itreator= (Iterable<Object>) obj;
			
			StringBuilder sb = new StringBuilder();
			sb.append("[ ");
			for(Object element : itreator){
				sb.append(ObjectString.toString(element) + " , ");
			}
			// �����һ��Ԫ�غ���ģ�ȥ��
			String s = sb.substring(0, sb.lastIndexOf(",") - 1);
			return s + " ]";
		}*/
		
		// �����һ����ͨ����Ļ�  ����ClassName[ fileName = fieldValue , --- ]����ʽ-Ҳ����ı���
		StringBuilder sb = new StringBuilder();
		// ���ƿ��أ�ֻ���������������������ӡ
		boolean flag = true;
		while(clazz != Object.class){
			if(flag){
				String className = clazz.getCanonicalName();
				sb.append(className + "[ ");
				flag = false;
			} 
			
			Field[] fieldArray = clazz.getDeclaredFields();
			
			for(Field field : fieldArray){
				try {
					sb.append(ObjectString.toString(field.getName()) + " = " + ObjectString.toString(field.get(obj)) + " , ");
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			clazz = clazz.getSuperclass();
			if(clazz == Object.class){
				String s = sb.substring(0, sb.lastIndexOf(",") - 1);
				sb = new StringBuilder().append(s + " ]");
			}
		}
		// �����һ��Ԫ�غ���ģ�ȥ��
		
		return sb.toString();
	}
	
	@SuppressWarnings("unused")
	private static boolean isImplementIterable(Class<?> clazz){
		/*if((clazz.isInterface() && clazz.getInterfaces().length == 0) 
				|| (clazz.getSuperclass() == Object.class && clazz.getInterfaces().length == 0)
				|| clazz == Object.class){*/
		if(clazz.getInterfaces() == null  || (clazz.getInterfaces().length == 0) 
				&& (/*clazz.getSuperclass() == Object.class || */clazz.isInterface() || clazz == Object.class)){
			return false;
		}else{
			// ������ʵ�ֵĽӿڰ�Iteralbe�ӿ�
			if(Arrays.asList(clazz.getInterfaces()).contains(Iterable.class)) {
				return true;
			}else{
				// �ֱ���ĳ���͸��ӿ��Ƿ�ʵ��Iterable�ӿ�
				boolean flag = isImplementIterable(clazz.getSuperclass());
				if(flag) return true;
				for(int i = 0;i < clazz.getInterfaces().length;i++){
					if(isImplementIterable(clazz.getInterfaces()[i])){
						return true;
					}
				}
				
				return false;
			}
		}
	}
	
	public static void main(String[] args) {
		String[] s = new String[]{"aaa" , "bbb" , "ccc"};
		List<String> array = new ArrayList<String>();
		array.add("aaa");
		array.add("aa1");
		
		Set<Nan> set = new HashSet<Nan>();
		set.add(new Nan());
		set.add(new Nan());
		System.out.println(ObjectString.toString(array));
		System.out.println(ObjectString.toString(set));
		System.out.println(ObjectString.toString(s));
		System.out.println(ObjectString.toString(new Integer(1)));
		System.out.println(ObjectString.toString(1));
		
	}
}

class Person{
	public int age = 2;
	public String aaa = "a";
}

class Nan extends Person{
	public String sex = "sex";
}
