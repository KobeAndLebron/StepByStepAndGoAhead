package com.cjs.gohead.spring.ioc.test;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;

public class TestDefaultListableBeanFactory {
	public static void main(String[] args) {
		
		/*DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		reader.loadBeanDefinitions(new FileSystemResource("Spring/com/cjs/gohead/spring/ioc/test/beans.xml"));

		// bring in some property values from a Properties file
		PropertyPlaceholderConfigurer cfg = new PropertyPlaceholderConfigurer();
		cfg.setLocation(new FileSystemResource("jdbc.properties"));

		// now actually do the replacement
		cfg.postProcessBeanFactory(factory);
		
		System.out.println(factory.getBean("test1"));*/
		
		@SuppressWarnings("resource")
		FileSystemXmlApplicationContext fsac = new FileSystemXmlApplicationContext("Spring/com/cjs/gohead/spring/ioc/test/beans.xml");
		System.out.println(fsac.getBean("test1"));
	}
}
