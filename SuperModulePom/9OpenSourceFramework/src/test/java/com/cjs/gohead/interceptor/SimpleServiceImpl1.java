package com.cjs.gohead.interceptor;

import com.cjs.gohead.interceptor.annotation.InterceptorName;
import com.cjs.gohead.interceptor.inter.Service;

@InterceptorName(interceptorNames={"com.cjs.gohead.spring.interceptor.examples.TimerInterceptor"})
public class SimpleServiceImpl1 implements Service{

	@Override
	public Object service() {
		System.out.println(getClass().getName() + " is executed!");
		return "Fail";
	}

}
