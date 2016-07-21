package com.cjs.gohead.interceptor.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.cjs.gohead.Service;
import com.cjs.gohead.interceptor.inter.InterceptorChain;

public class ServiceInvocationHandler implements InvocationHandler{
	private InterceptorChain interceptorChain;
	private Service service;
	
	public ServiceInvocationHandler(InterceptorChain interceptorChain, Service service){
		this.interceptorChain = interceptorChain;
		this.service = service;
	}
	
	public ServiceInvocationHandler() {
	}
	
	public void setInterceptorChain(InterceptorChain interceptorChain){
		this.interceptorChain = interceptorChain;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return interceptorChain.doInterceptor();
	}

}
