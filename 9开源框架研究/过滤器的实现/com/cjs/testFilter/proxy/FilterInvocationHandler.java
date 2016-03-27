package com.cjs.testFilter.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import javax.servlet.Filter;

/**
 * 每一个由java产生的代理类里面都有一个InvocationHandler对象，当代理类的方法（代理接口里的方法）被调用时，就会执行它里面的invocationhandler对象的invoke方法。
 *
 * @author 陈景帅
 *
 * 每天进步一点——2016年1月2日
 *
 */
public class FilterInvocationHandler implements InvocationHandler{
	Filter targetFilter;
	
	public void setTargetFilter(Filter targetFilter){
		this.targetFilter = targetFilter;
	}
	
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println(targetFilter + "的" + method.getName() + "方法被执行开始");
//		System.out.println(proxy.getClass().getDeclaredFields()[3].getType());
		Object returnObj = method.invoke(targetFilter, args);
		
		System.out.println(targetFilter + "的" + method.getName() + "方法执行完毕");
		
		return returnObj;
	}

}
