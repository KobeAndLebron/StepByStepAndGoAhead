package com.cjs.gohead.interceptor.storage;

import java.util.HashMap;
import java.util.Map;

import com.cjs.gohead.Service;
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
	public static void initServiceAndInterceptors(){
		
	}
	
	/**
	 * 根据ServiceName来获取对应的Interceptor数组
	 * @param service
	 * @return
	 */
	public static InterceptorChain getInterceptor(String service){
		Class<?>[] interceptorClazzs = serviceInterceptors.get(service);
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
		Service serviceObj = null;
		try {
			serviceObj = (Service) Class.forName(service).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Unknown class");
		} catch (ClassCastException e) {
			System.out.println("Unknown service");
		}
		interceptorChain.setSercice(serviceObj);
		return interceptorChain;
	}
}
