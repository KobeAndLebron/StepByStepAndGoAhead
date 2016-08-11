package com.cjs.goHead.observer.basic_example.impl;

import java.util.EventObject;

import com.cjs.goHead.observer.basic_example.inter.ServletContext;

public class ServletContextEvent extends EventObject{

	private static final long serialVersionUID = 5669108178824898803L;

	public ServletContextEvent(ServletContext source) {
		super(source);
	}

	public ServletContext getServletContext(){
		return (ServletContext) source;
	}
}
