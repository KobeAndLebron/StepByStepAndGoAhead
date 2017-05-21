package javax.servlet;

/**
 * 	Define an object to provide com.cjs.gohead.mybatis.client request information to a servlet.The servlet container create a ServletRequest and pass it as
 * an argument to Servlet's service method.
 *  
 *  Interfaces that extend ServletRequest can provide additional protocol-specific data(for example , HTTP data is provided by 
 *  HttpServletRequest)
 * 
 *
 * @author 陈景帅
 *
 * 每天进步一点——2016年1月2日
 *
 */
public interface ServletRequest {
	/**
	 * This method must be called prior to reading request parameters or reading input using getReader().
	 * 必须在获取参数之前调用此方法
	 * @param enc
	 * 
	 * java.io.UnsupportedEncodingException - if this is not a valid encoding
	 */
	public void setCharacterEncoding(String enc);
}
