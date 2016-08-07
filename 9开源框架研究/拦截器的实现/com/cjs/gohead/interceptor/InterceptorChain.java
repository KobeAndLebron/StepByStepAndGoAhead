package com.cjs.gohead.interceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cjs.gohead.Service;

/**
 * 拦截器链
 * @author ChenJingShuai  
 *
 * Make a bit of progress every day. 2016年7月21日-下午7:53:39
 *
 */
public class InterceptorChain {
	private Object[] params;
	private Method method;
	private Service service;
	private List<Interceptor> interceptors = new ArrayList<>(10);
	int index = 0;
	
	public void addInterceptor(Interceptor interceptor){
		interceptors.add(interceptor);
	}
	
	public void setSercice(Service service){
		this.service = service;
	}
	
	public Iterator<Interceptor> getInterceptorsIterator(){
		return this.interceptors.iterator();
	}
	
}
