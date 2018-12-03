package com.cjs.testFilter.proxy;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cjs.core.ApplicationFilterChain;
import com.cjs.testFilter.EncodingFilter;

/*
 * 利用动态代理来为记录方法的执行记录
 */
public class TestFIlter1{
	public static void main(String[] args) throws NoSuchFieldException , IllegalAccessException, IllegalArgumentException, NoSuchMethodException, InstantiationException, InvocationTargetException{
		FilterChain filterChain = new ApplicationFilterChain();
		
		/***************web容器生成ServletResponse和ServletRequest对象************/
		ServletRequest servletRequest = new HttpServletRequest();
		ServletResponse servletResponse = new HttpServletResponse();
		
		
		/***************web容器生成ServletResponse和ServletRequest对象************/
		/************web容器读取web.xml并且实例化Filter将其放入filterChain中********/
		// 容器根据web.xml使用反射来生成对象
		Filter encodingFilter = ProxyFilterFactory.getProxyFilterByTatget(new EncodingFilter());
		// filter1.init(filterChain);
		
		filterChain.addFilter(encodingFilter);
		
		Filter encodigFiler1 = ProxyFilterFactory.getProxyFilterByTatget(new EncodingFilter());
		
		filterChain.addFilter(encodigFiler1);
		/************web容器读取web.xml并且实例化Filter将其放入filterChain中********/
		
		filterChain.doFilter(servletRequest, servletResponse);
		
		Class<? extends ServletRequest> httpServletRequestClazz = HttpServletRequest.class;
		Field encodingField = httpServletRequestClazz.getDeclaredField("encoding");
		// encodingField.setAccessible(true);
		Field.setAccessible(new Field[]{encodingField}, true);
		System.out.println(encodingField.get(servletRequest));
	}
}
