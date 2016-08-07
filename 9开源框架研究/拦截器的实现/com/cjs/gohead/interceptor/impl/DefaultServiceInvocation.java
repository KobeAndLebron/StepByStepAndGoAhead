package com.cjs.gohead.interceptor.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

import com.cjs.gohead.interceptor.inter.Interceptor;
import com.cjs.gohead.interceptor.inter.InterceptorChain;
import com.cjs.gohead.interceptor.inter.ServiceInvocation;
import com.cjs.gohead.interceptor.inter.ServiceProxy;
import com.cjs.gohead.interceptor.storage.ServiceInterceptorMap;

public class DefaultServiceInvocation implements ServiceInvocation {
	private Iterator<Interceptor> interceptors;
	private Object service;
	private Object result;
	private ServiceProxy serviceProxy;
	
	public DefaultServiceInvocation() {
	
	};
	
	@Override
	public Object getService() {
		return service;
	}

	@Override
	public Object getResult() {
		return result;
	}

	@Override
	public Object invoke() {
		Object result = null;
		if(interceptors.hasNext()){
			Interceptor interceptor = interceptors.next();
			result = interceptor.interceptor(this);
		}else{
			result = invokeActionOnly();
		}
		return result;
	}

	@Override
	public Object invokeActionOnly() {
		Object result = null;
		Method method = null;
		try {
			method = service.getClass().getMethod(serviceProxy.getMethodName());
			result = method.invoke(service);
		} catch (NoSuchMethodException e) {
			System.out.println("Illegal method for " + serviceProxy.getActionClassName());
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void init(ServiceProxy serviceProxy) {
		InterceptorChain interceptorChain = ServiceInterceptorMap.getInterceptorChain(serviceProxy.getActionClassName());
		interceptors = interceptorChain.getInterceptorsIterator();
		this.serviceProxy = serviceProxy;
		try {
			service = Class.forName(serviceProxy.getActionClassName()).newInstance();
		} catch (InstantiationException e) {
			System.out.println("Unable to initialize Action!");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.out.println("Illegal access to constructor, is it Public?");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("No action defined for " + serviceProxy.getActionClassName());
			e.printStackTrace();
		}
	}

	
	public ServiceProxy getServiceProxy(){
		return this.serviceProxy;
	}
}
