package com.cjs.gohead.interceptor.impl;

import com.cjs.gohead.interceptor.inter.ServiceInvocation;
import com.cjs.gohead.interceptor.inter.ServiceProxy;

public class DefaultServiceProxy implements ServiceProxy {
	private String methodName;
	private String actionClassName;
	private ServiceInvocation serviceInvocation;
	
	public DefaultServiceProxy(ServiceInvocation serviceInvocation, String methodName, String actionClassName) {
		this.methodName = methodName;
		this.actionClassName = actionClassName;
		this.serviceInvocation = serviceInvocation;
	}
	
	
	public void prepare(){
		this.serviceInvocation.init(this);
	}
	
	@Override
	public String getMethodName() {
		return methodName;
	}

	@Override
	public String getActionClassName() {
		return actionClassName;
	}

	@Override
	public Object execute() {
		return serviceInvocation.invoke();
	}

}
