package com.cjs.goHead.observer.basic_example.inter;

/**
 * @see {@linkplain javax.servlet.ServletContext}
 * 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年8月3日-下午10:50:10
 */
public interface ServletContext {
	public <T extends ServletContextListener> void addListener(T t);
	public String getName();
	public void initServletContext();
	public void destoryServletContext();
}
