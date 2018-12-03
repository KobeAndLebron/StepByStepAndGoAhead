package com.cjs.goHead.observer.basic_example;

import com.cjs.goHead.observer.basic_example.impl.MyServletContext;
import com.cjs.goHead.observer.basic_example.impl.MyServletContextListener;
import com.cjs.goHead.observer.basic_example.inter.ServletContext;
import com.cjs.goHead.observer.basic_example.inter.ServletContextListener;

public class Client {
	public static void main(String[] args) {
		ServletContext sc = new MyServletContext("servletContext1");
		
		ServletContextListener mcl = new MyServletContextListener();
		
		sc.addListener(mcl);
		
		sc.initServletContext();
		sc.destroyServletContext();
	}
}
