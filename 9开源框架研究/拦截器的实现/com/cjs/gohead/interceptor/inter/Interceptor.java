package com.cjs.gohead.interceptor.inter;

import com.cjs.gohead.Service;

public interface Interceptor {
	public Object doInterceptor(Service service, InterceptorChain interceptorChain);
}
