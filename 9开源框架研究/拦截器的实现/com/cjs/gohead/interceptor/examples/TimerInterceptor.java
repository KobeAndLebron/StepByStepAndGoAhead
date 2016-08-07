package com.cjs.gohead.interceptor.examples;

import com.cjs.gohead.interceptor.inter.ServiceInvocation;

public class TimerInterceptor extends AbstractInterceptor{

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
