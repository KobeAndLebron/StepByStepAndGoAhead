package com.cjs.gohead;

import com.cjs.gohead.interceptor.annotation.Interceptor;

@Interceptor(interceptorName="")
public class SimpleServiceImpl implements Service{
	@Override
	public Object service(String request, String reponse) {
		return null;
	}

}
