package com.cjs.gohead.interceptor.storage;

import java.util.HashMap;
import java.util.Map;

import com.cjs.gohead.interceptor.inter.Interceptor;
import com.cjs.gohead.interceptor.inter.InterceptorChain;

public class ServiceInterceptorMap {
	/**
	 * 服务和拦截器的映射
	 */
	private static Map<String, Class<?>[]> serviceInterceptors = new HashMap<>();
	
	/**
	 * 加载注解并存储
	 */
	static {
		serviceInterceptors.put("com.cjs.gohead.interceptor.SimpleServiceImpl", 
				new Class<?>[]{com.cjs.gohead.interceptor.examples.TimerInterceptor.class});
	}
	
	/**
	 * 根据ServiceName来获取对应的Interceptor数组
	 * @param service
	 * @return
	 */
	public static InterceptorChain getInterceptorChain(String serviceClassName){
		Class<?>[] interceptorClazzs = serviceInterceptors.get(serviceClassName);
		InterceptorChain interceptorChain = new InterceptorChain();
		for(Class<?> interceptorClazz : interceptorClazzs){
			Interceptor interceptor = null;
			try {
				interceptor = (Interceptor) interceptorClazz.newInstance();
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			} catch (ClassCastException e){
				System.out.println(interceptorClazz.getName() + "is not a interceptor.");
			}
			interceptorChain.addInterceptor(interceptor);
		}
		return interceptorChain;
	}
}
