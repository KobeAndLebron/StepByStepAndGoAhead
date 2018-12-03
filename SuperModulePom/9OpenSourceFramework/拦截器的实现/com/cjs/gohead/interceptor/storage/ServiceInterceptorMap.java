package com.cjs.gohead.interceptor.storage;

import com.cjs.gohead.interceptor.annotation.InterceptorName;
import com.cjs.gohead.interceptor.inter.Interceptor;
import com.cjs.gohead.interceptor.inter.InterceptorChain;
import com.gohead.shared.annotation.AnnotationUtils;

public class ServiceInterceptorMap {
	/**
	 * 加载注解并存储
	 */
	static {
		/*System.out.println(AnnotationUtils.findAnnotation(com.cjs.gohead.interceptor.SimpleServiceImpl.class,
				com.cjs.gohead.interceptor.annotation.InterceptorName.class));*/
		
	}
	
	private static String[] getInterceptorClassNames(Class<?> serviceClass){
		InterceptorName interceptorAnnotation = AnnotationUtils.findAnnotation(serviceClass, 
				com.cjs.gohead.interceptor.annotation.InterceptorName.class);
		String[] interceptorClassNames = null;
		if(interceptorAnnotation != null){
			interceptorClassNames = interceptorAnnotation.interceptorNames();
		}
		return interceptorClassNames;
	}
	
	private static String[] getInterceptorClassNamesByServiceClassName(String serviceClassName){
		Class<?> serviceClass = null;
		try {
			serviceClass = Class.forName(serviceClassName);
		} catch (ClassNotFoundException e) {
			System.out.println("Non-exist class: " + serviceClassName);
			e.printStackTrace();
		}
		return getInterceptorClassNames(serviceClass);
	}
	
	/**
	 * 根据ServiceName来获取对应的Interceptor数组
	 * @param service
	 * @return
	 */
	public static InterceptorChain getInterceptorChain(String serviceClassName){
		InterceptorChain interceptorChain = new InterceptorChain();
		String[] interceptorClassNames = getInterceptorClassNamesByServiceClassName(serviceClassName);
		Class<?> interceptorClazz= null;
		Interceptor interceptor = null;
		if(interceptorClassNames != null){
			for(String interceptorClassName : interceptorClassNames){
				try {
					interceptorClazz = Class.forName(interceptorClassName);
					interceptor = (Interceptor) interceptorClazz.newInstance();
					interceptor.init();
					interceptorChain.addInterceptor(interceptor);
				} catch (ClassNotFoundException e) {
					System.out.println("Non-exist class: " + interceptorClassName);
				} catch (InstantiationException e) {
					System.out.println("Unable to initialize class: " + interceptorClassName);
				} catch (IllegalAccessException e) {
					System.out.println("Empty constructor for " + interceptorClassName + " is not Public!");
				} catch (ClassCastException e){
					System.out.println(interceptorClassName + "is not a interceptor.");
				}
			}
		}
		return interceptorChain;
	}
}
