package com.cjs.gohead.interceptor;

import org.junit.Test;

import com.cjs.gohead.interceptor.factory.ServiceProxyFactory;
import com.cjs.gohead.interceptor.inter.ServiceInvocation;
import com.cjs.gohead.interceptor.inter.ServiceProxy;

public class Client {
	
	@Test
	public void test(){
		ServiceInvocation serviceInvocation = ServiceProxyFactory.getServiceInvocation();
		ServiceProxy serviceProxy = ServiceProxyFactory.generateService(serviceInvocation, 
						"com.cjs.gohead.interceptor.SimpleServiceImpl", "service");
		serviceProxy.execute();
		serviceProxy.execute();
	}
}
