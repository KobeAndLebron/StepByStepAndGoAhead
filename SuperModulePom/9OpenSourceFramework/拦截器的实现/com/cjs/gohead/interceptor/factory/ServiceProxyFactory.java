package com.cjs.gohead.interceptor.factory;

import com.cjs.gohead.interceptor.impl.DefaultServiceInvocation;
import com.cjs.gohead.interceptor.impl.DefaultServiceProxy;
import com.cjs.gohead.interceptor.inter.ServiceInvocation;
import com.cjs.gohead.interceptor.inter.ServiceProxy;

public class ServiceProxyFactory {
	public static ServiceInvocation getServiceInvocation(){
		return new DefaultServiceInvocation();
	}
	
	public static ServiceProxy generateService(ServiceInvocation serviceInvocation,
			String actionClassName, String methodName){
		
		DefaultServiceProxy proxy = new DefaultServiceProxy(serviceInvocation, methodName, actionClassName);
		
		proxy.prepare();
		
		return proxy;
	}
}
