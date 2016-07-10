package com.cjs.goHead.ioc.test;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.FileSystemResource;

public class TestDefaultListableBeanFactory {
	public static void main(String[] args) {
		
		DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		reader.loadBeanDefinitions(new FileSystemResource("Spring/com/cjs/goHead/ioc/test/beans.xml"));

		/*// bring in some property values from a Properties file
		PropertyPlaceholderConfigurer cfg = new PropertyPlaceholderConfigurer();
		cfg.setLocation(new FileSystemResource("jdbc.properties"));

		// now actually do the replacement
		cfg.postProcessBeanFactory(factory);*/
		
		System.out.println(factory.getBean("test1"));
	}
}
