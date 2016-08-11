package com.cjs.gohead.ioc;

/**
 *  BeanFactory(resides in spring-beans) And ApplicationContext(resides in spring-context):
 *  The org.springframework.beans and org.springframework.context packages are the basis for Spring Framework’s IoC 
 * container. The BeanFactory interface provides an advanced configuration mechanism capable of managing any type 
 * of object. ApplicationContext is a sub-interface of BeanFactory. It adds easier integration with Spring’s AOP 
 * features; message resource handling (for use in internationalization), event publication; and application-layer
 * specific contexts such as the WebApplicationContext for use in web applications.
 *  In short, the BeanFactory provides the configuration framework and basic functionality, and the ApplicationContext 
 * adds more enterprise-specific functionality.
 *  Because the ApplicationContext includes all functionality of the BeanFactory, it is generally recommended over 
 * the BeanFactory, except for a few situations such as in embedded applications running on resource-constrained 
 * devices where memory consumption might be critical and a few extra kilobytes might make a difference. However, 
 * for most typical enterprise applications and systems, the ApplicationContext is what you will want to use. 
 *  
 *  
 * 	The root interface for accessing a Spring bean container.
 *	The interface is implemented by objects that hold a number
 * of bean definitions,each uniquely identified by a String name.
 * Depending on the Bean definition,the factory will return either
 * an independent instance of a contained object(The prototype de-
 * sign pattern),or a single shared instance(a superior alternative
 * to the Singleton design pattern,in which the instance is a sinle-
 * ton in the scope of the factory).Since spring 2.0,further scopes
 * are available depending on the concrete application context(e.g.
 * "request" and "session" scopes in a web environment)
 * 
 *  Bean Factory implementations should support the standard bean 
 * lifecycle interfaces as far as possible.The full set of initialization
 * methods and their standard order is :
	 * 1. BeanNameAware's {@code setBeanName}<br>
	 * 2. BeanClassLoaderAware's {@code setBeanClassLoader}<br>
	 * 3. BeanFactoryAware's {@code setBeanFactory}<br>
	 * 4. ResourceLoaderAware's {@code setResourceLoader}
	 * (only applicable when running in an application context)<br>
	 * 5. ApplicationEventPublisherAware's {@code setApplicationEventPublisher}
	 * (only applicable when running in an application context)<br>
	 * 6. MessageSourceAware's {@code setMessageSource}
	 * (only applicable when running in an application context)<br>
	 * 7. ApplicationContextAware's {@code setApplicationContext}
	 * (only applicable when running in an application context)<br>
	 * 8. ServletContextAware's {@code setServletContext}
	 * (only applicable when running in a web application context)<br>
	 * 9. {@code postProcessBeforeInitialization} methods of BeanPostProcessors<br>
	 * 10. InitializingBean's {@code afterPropertiesSet}<br>
	 * 11. a custom init-method definition<br>
	 * 12. {@code postProcessAfterInitialization} methods of BeanPostProcessors
 *
 * <p>On shutdown of a bean factory, the following lifecycle methods apply:<br>
	 * 1. DisposableBean's {@code destroy}<br>
	 * 2. a custom destroy-method definition
 * 
 * @author ChenJingShuai  
 *
 * Make a bit of progress every day. 2016年7月9日-上午11:35:19
 *
 */
public interface MyBeanFactory {
	/**
	 *  Return an instance,which may be shared or independent,of the specified bean.
	 *  This method allows a Spring BeanFactory to be used as a replacement for the
	 * Singleton or Prototype design pattern. Callers may retain references to
	 * returned objects in the case of Singleton beans.
	 * 
	 * @param name the name of the bean to retrieve
	 * @return an instance of the bean identified by the specified string name
	 */
	public Object getBean(String name);
	
	/**
	 *  Is this bean a shared singleton?That is,will {@link #getBean(String)} always 
	 * return the same instance?
	 * Note: This method returning {@code false} does not clearly indicate
	 * independent instances. It indicates non-singleton instances, which may correspond
	 * to a scoped bean as well. Use the {@link #isPrototype} operation to explicitly
	 * check for independent instances.
	 * 
	 * @param name the name of the bean to query
	 * @return whether this bean corresponds to a singleton instance
	 * @see #getBean(String)
	 * @see #isPrototype(String)
	 */
	public boolean isSingleton(String name);
	
	/**
	 *  Is this bean a prototype?That is,will {@link #getBean(String)} always return
	 * independent instance?
	 * Note: This method returning {@code false} does not clearly indicate
	 * a singleton object. It indicates non-independent instances, which may correspond
	 * to a scoped bean as well. Use the {@link #isSingleton} operation to explicitly
	 * check for a shared singleton instance.
	 * 
	 * @param name the name of the bean to query
	 * @return whether this bean will always deliver independent instances
	 * @see #getBean(String)
	 * @see #isSingleton(String)
	 */
	public boolean isPrototype(String name);
	
	/**
	 * Does this bean factory contain a bean definition instance with the given name?
	 * @param name the name of the bean to query
	 * @return whether a bean with the given name is present
	 */
	public boolean containsBean(String name);
}
