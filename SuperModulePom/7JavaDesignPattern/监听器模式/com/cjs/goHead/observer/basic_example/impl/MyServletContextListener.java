package com.cjs.goHead.observer.basic_example.impl;

import com.cjs.goHead.observer.basic_example.inter.ServletContextListener;

public class MyServletContextListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println(sce.getServletContext().getName() + " is initialized.");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println(sce.getServletContext().getName() + " is destoryed.");
	}

}
