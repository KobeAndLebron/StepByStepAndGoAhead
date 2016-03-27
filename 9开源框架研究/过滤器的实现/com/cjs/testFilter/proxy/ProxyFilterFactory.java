package com.cjs.testFilter.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

import javax.servlet.Filter;

/**
 * 产生代理filter的工厂类
 *
 * @author 陈景帅
 *
 * 每天进步一点——2016年1月2日
 *
 */
public class ProxyFilterFactory {
	public static Filter getProxyFilterByTatget(Filter filter) throws NoSuchMethodException ,IllegalAccessException, IllegalArgumentException,
												InstantiationException, InvocationTargetException {
		Class<?> proxyClazz = Proxy.getProxyClass(ProxyFilterFactory.class.getClassLoader(), Filter.class);
		Constructor<?> invocationHanlderConstructor = proxyClazz.getConstructor(InvocationHandler.class);
		
		FilterInvocationHandler filterInvocationHandler = new FilterInvocationHandler();
		filterInvocationHandler.setTargetFilter(filter);
		
		return (Filter)invocationHanlderConstructor.newInstance(new Object[]{filterInvocationHandler});
	}
}

