package com.cjs.goHead.observer.basic_example.impl;

import java.util.ArrayList;
import java.util.List;

import com.cjs.goHead.observer.basic_example.inter.ServletContext;
import com.cjs.goHead.observer.basic_example.inter.ServletContextListener;

public class MyServletContext implements ServletContext {
	private List<ServletContextListener> listeners = new ArrayList<>();
	private String servletContextName;
		
	public MyServletContext(String servletContextName) {
		this.servletContextName = servletContextName;
	}
	
	public MyServletContext() {
		this.servletContextName = "defaultServlet";
	}
	
	@Override
	public <T extends ServletContextListener> void addListener(T t) {
		listeners.add(t);
	}

	public void initServletContext(){
		ServletContextEvent sce = new ServletContextEvent(this);
		for(ServletContextListener listener : listeners){
			listener.contextInitialized(sce);
		}
	}
	
	public void destroyServletContext(){
		ServletContextEvent sce = new ServletContextEvent(this);
		for(ServletContextListener listener : listeners){
			listener.contextDestroyed(sce);
		}
	}

	@Override
	public String getName() {
		return servletContextName;
	}
}
