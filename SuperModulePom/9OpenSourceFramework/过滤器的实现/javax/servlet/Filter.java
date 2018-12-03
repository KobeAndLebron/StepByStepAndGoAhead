package javax.servlet;

/**
 * The filter is a object performs filtering task on either the request to a resource(a servlet or a static content),or on the response from 
 * a resource,or both.
 * 
 * Filters performs filtering in the doFilter method.Every filter has access to a FilterChain object from which it can obtain its initialization
 * parameters,a reference to ServletContext which it can use,for examples,to load resources needed for filtering tasks. 
 *
 * Filters are configured in the deployment descriptor of a web application 
 * @author 陈景帅
 *
 * 每天进步一点——2016年1月2日
 *
 */
public interface Filter {
	public abstract void doFilter(ServletRequest servletRequest ,ServletResponse servletResponse ,FilterChain filterChain);
	
	
	// 依赖注入，Filter依赖于FilterConfig，利用Servlet容器来将FilterConfig注入到filter中。
	// 这样做的好处：将FilterConfig直接移动到配置文件中，将程序创造FilterConfig的权利转交给Servlet容器
	// public void init(FilterConfig filterConfig);
}
