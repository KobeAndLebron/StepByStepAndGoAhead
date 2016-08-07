package com.cjs.gohead.interceptor.inter;

import com.opensymphony.xwork2.ActionInvocation;

/**
 * 	An interceptor is a stateless class that follows the interceptor pattern, as
 * found in {@link javax.servlet.Filter} and in AOP languages.
 * 
 * 	Interceptors are objects that dynamically intercept Action invocations. They
 * provide the developer with the opportunity to define code that can be
 * executed before and/or after the execution of an action. They also have the
 * ability to prevent an action from executing. Interceptors provide developers
 * a way to encapulate common functionality in a re-usable form that can be
 * applied to one or more Actions.
 *
 * 	Interceptors <b>must</b> be stateless and not assume that a new instance will
 * be created for each request or Action. Interceptors may choose to either
 * short-circuit the {@link ActionInvocation} execution and return a return code
 * (such as {@link com.opensymphony.xwork2.Action#SUCCESS}), or it may choose to
 * do some processing before and/or after delegating the rest of the procesing
 * using {@link ActionInvocation#invoke()}.
 * 
 * @author ChenJingShuai
 *
 *         Make a bit of progress every day. 2016年8月7日-上午10:03:56
 *
 */
public interface Interceptor {
	public Object interceptor(ServiceInvocation serviceInvocation);
	
	void init();
	
	void destory();
}
