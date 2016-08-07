package com.cjs.gohead.interceptor;

import com.cjs.gohead.interceptor.annotation.Interceptor;
import com.cjs.gohead.interceptor.inter.Service;

@Interceptor(interceptorNames={"com.cjs.gohead.interceptor.examples.TimerInterceptor"})
public class SimpleServiceImpl implements Service{

	@Override
	public Object service() {
		System.out.println(getClass().getName() + " is executed!");
		return "Success";
	}

}
