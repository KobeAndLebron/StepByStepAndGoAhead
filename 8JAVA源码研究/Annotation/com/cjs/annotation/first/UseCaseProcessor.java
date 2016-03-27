package com.cjs.annotation.first;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * {@linkplain UseCase}注解的处理类
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年3月26日
 */
public class UseCaseProcessor {
	public static void processUseCaseAnnotation(List<Integer> useCases ,Class<?> clazz){
		for(Method m : clazz.getDeclaredMethods()){
			UseCase uc = m.getAnnotation(UseCase.class);
			if(uc != null) {
				System.out.println("Found Use Case:" + uc.id() + " , " + uc.description());
				useCases.remove(uc.id());
			}
		}
		for(int i : useCases){
			System.out.println("Warning: Missing use case-" + i);
		}
	}
	
	public static void main(String[] args) {
		List<Integer> useCases = new ArrayList<Integer>();
		Collections.addAll(useCases, 1, 2, 3);
		processUseCaseAnnotation(useCases, UseUseCaseAnnotation.class);
	}
}
