package com.cjs.gohead.interceptor.factory;

import com.cjs.gohead.Service;
import com.cjs.gohead.interceptor.inter.InterceptorChain;
import com.cjs.gohead.interceptor.storage.ServiceInterceptorMap;

public class ServiceFactory {
	public Service generateService(String serviceName){
		InterceptorChain interceptorChain = ServiceInterceptorMap.getInterceptor(serviceName);
		
	}
}
