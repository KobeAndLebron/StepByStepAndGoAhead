package com.cjs.gohead.interceptor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cjs.gohead.interceptor.annotation.InterceptorName;



/**
 * 拦截器链
 * @author ChenJingShuai  
 *
 * Make a bit of progress every day. 2016年7月21日-下午7:53:39
 *
 */
public class InterceptorChain {
	private List<InterceptorName> interceptors = new ArrayList<>(10);
	
	public void addInterceptor(InterceptorName interceptor){
		interceptors.add(interceptor);
	}
	
	public Iterator<InterceptorName> getInterceptorsIterator(){
		return this.interceptors.iterator();
	}
	
}
