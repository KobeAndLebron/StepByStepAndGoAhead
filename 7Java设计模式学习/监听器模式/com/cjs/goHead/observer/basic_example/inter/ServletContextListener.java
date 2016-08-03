package com.cjs.goHead.observer.basic_example.inter;

import java.util.EventListener;

import com.cjs.goHead.observer.basic_example.impl.ServletContextEvent;

/**
 * Interface for receiving notification events about ServletContext lifecycle changes.
 * @author ChenJingShuai 3 Aug 2016
 *
 */
public interface ServletContextListener extends EventListener{
	public abstract void contextInitialized(ServletContextEvent sce);
	
	public void contextDestroyed(ServletContextEvent sce);
}
