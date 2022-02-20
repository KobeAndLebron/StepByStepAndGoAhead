package com.cjs.core;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.ProxyMethodInvocation;
import org.springframework.aop.framework.InterceptorAndDynamicMethodMatcher;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.List;

/**
 * 相当于AOP当中的{@linkplain org.springframework.aop.framework.CglibAopProxy#CglibMethodInvocation}
 * AOP中的拦截器是
 */
public class ApplicationFilterChain implements FilterChain{

    /***************** AOP 中拦截器链的关键实现 *******************/
    abstract class ReflectiveMethodInvocation implements ProxyMethodInvocation, Cloneable {

        /**
         * List of {@linkplain org.aopalliance.intercept.MethodInterceptor} and InterceptorAndDynamicMethodMatcher
         * that need dynamic checks.
         */
        protected List<MethodInterceptor> interceptorsAndDynamicMethodMatchers;

        /**
         * Index from 0 of the current interceptor we're invoking.
         * -1 until we invoke: then the current interceptor.
         */
        private int currentInterceptorIndex = -1;

        public Object proceed () throws Throwable {
            //	We start with an index of -1 and increment early.
            if (this.currentInterceptorIndex == this.interceptorsAndDynamicMethodMatchers.size() - 1) {
                // return invokeJoinpoint();
            }

            Object interceptorOrInterceptionAdvice =
                this.interceptorsAndDynamicMethodMatchers.get(++this.currentInterceptorIndex);
            // if (interceptorOrInterceptionAdvice instanceof InterceptorAndDynamicMethodMatcher) {
            // 忽略if中的东西.
            // else {
            // It's an interceptor, so we just invoke it: The pointcut will have
            // been evaluated statically before this object was constructed.
            return ((MethodInterceptor) interceptorOrInterceptionAdvice).invoke(this);
            // }
        }
    }
	/***************** AOP 中拦截器链的关键实现 *******************/

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
