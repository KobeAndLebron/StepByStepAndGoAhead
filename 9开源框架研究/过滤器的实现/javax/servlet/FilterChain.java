package javax.servlet;


public interface FilterChain {
	// Causes the next filter in the chain to be invoked,
	// or if the calling filter is the last filter in the chain, causes the resource（Servlet or static content） at the end of the chain to be invoked
	public void doFilter(ServletRequest servletRequest ,ServletResponse servletResponse);
	
	public void addFilter(Filter filter);
}
