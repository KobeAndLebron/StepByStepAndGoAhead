package com.cjs.gohead.interceptor.inter;

public interface ServiceProxy {
	String getMethodName();
	
	String getActionClassName();
	
	Object execute();
}
