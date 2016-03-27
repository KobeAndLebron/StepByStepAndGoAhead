package com.cjs.core;

import java.lang.annotation.Documented;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ApplicationFilterChain implements FilterChain{
	private Filter[] filters;
	private int size;
	private int index;
	
	public ApplicationFilterChain() {
		filters = new Filter[10];
		size = index = 0;
	}
	
	/**
	 * 添加过滤器
	 * @param filter
	 */
	public void addFilter(Filter filter){
		// 如果数组装满了
		if(filters.length == size){
			 //  扩容
			 Filter[] newFilters = new Filter[2 * size + 1];
			 System.arraycopy(filters, 0, newFilters, 0, size);
			 filters = newFilters;
		}
		filters[size++] = filter;
	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse) {
		// 如果过滤器已经执行完毕
		if(index == size){
			System.out.println("执行Servlet或寻找静态资源文件");
			// 执行完毕后将拦截器的索引归0
			index = 0;
			return;
		}
		Filter nowFilter = filters[index++];
		nowFilter.doFilter(servletRequest, servletResponse, this);
		
	}
	
}
