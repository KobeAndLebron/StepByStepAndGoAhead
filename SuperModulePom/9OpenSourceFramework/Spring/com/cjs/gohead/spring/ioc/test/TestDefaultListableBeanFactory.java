package com.cjs.gohead.spring.ioc.test;

import com.cjs.gohead.spring.ioc.components_scan_and_auto_wired.example.config.ComponentScanConfig;
import com.cjs.gohead.spring.ioc.components_scan_and_auto_wired.example.soundsystem.inter.Tire;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

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
        ClassPathXmlApplicationContext fsac = new ClassPathXmlApplicationContext("com/cjs/gohead/spring/ioc/test/beans.xml");
		System.out.println(fsac.getBean("test1"));

        AnnotationConfigApplicationContext applicationContext =
            new AnnotationConfigApplicationContext(ComponentScanConfig.class);
        System.out.println(applicationContext.getBean(Tire.class));

        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        System.out.println(Arrays.toString(beanDefinitionNames));
    }
}
