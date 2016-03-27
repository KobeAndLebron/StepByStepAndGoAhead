package com.cjs.annotation.first;

/**
 * 使用{@linkplain UseCase}注解的类
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年3月26日
 */
public class UseUseCaseAnnotation {
	
	@UseCase(id = 1)
	public boolean validatePassword(String password){
		return true;
	}
	
	@UseCase(id = 2 , description = "this method is to validate password")
	public boolean validatePassword1(String password){
		return true;
	}
}
