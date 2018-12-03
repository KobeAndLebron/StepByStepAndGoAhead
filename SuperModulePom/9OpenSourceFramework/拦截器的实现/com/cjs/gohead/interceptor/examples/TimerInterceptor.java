package com.cjs.gohead.interceptor.examples;

import com.cjs.gohead.interceptor.inter.ServiceInvocation;

public class TimerInterceptor extends AbstractInterceptor{
	
	/**
	 * Must be public and is called by 
	 * {@linkplain com.cjs.gohead.spring.interceptor.storage.ServiceInterceptorMap#getInterceptorChain(String) 生成拦截器对象的方法}
	 */
	public TimerInterceptor() {
	}
	
	@Override
	public Object interceptor(ServiceInvocation serviceInvocation) {
		Object result;
		long start = System.currentTimeMillis();
		result = serviceInvocation.invoke();
		long end = System.currentTimeMillis();
		System.out.println(serviceInvocation.getServiceProxy().getActionClassName() + "'s " + 
				serviceInvocation.getServiceProxy().getMethodName() + " 's executed time is " + 
				(end - start) + "ms");
		return result;
	}

}
