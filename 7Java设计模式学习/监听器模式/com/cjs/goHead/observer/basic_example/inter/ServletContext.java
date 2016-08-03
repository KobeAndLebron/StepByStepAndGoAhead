package com.cjs.goHead.observer.basic_example.inter;

public interface ServletContext {
	public <T extends ServletContextListener> void addListener(T t);
	public String getName();
	public void initServletContext();
	public void destoryServletContext();
}
