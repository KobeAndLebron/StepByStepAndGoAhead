package com.cjs.gohead.interceptor.inter;

public interface ServiceInvocation {
	Object getService();

	Object getResult();

	Object invoke();

	Object invokeActionOnly();

	void init(ServiceProxy serviceProxy);
	
	ServiceProxy getServiceProxy();
}
