package javax.servlet;

/**
 * Define an object to assist a servlet in sending a response to com.cjs.gohead.mybatis.client.The servlet container create a ServletResponse object and passes
 * it to the servlet's service method.  
 *
 * To send binary data in a MIME body response,use the ServletOutputStream returned by getOutPutStream();
 * To send character data,use the the PrintWriter returned by getWriter().
 * 
 * The charset for the MIME body response can be specified explicitly using the setCharacterEncoding and setContentType methods 
 * If no charset specified,ISO-8859-1 will be used;
 * These two methods must be called before getWriter and before committing the response for the character encoding to  be used.
 * 
 * @author 陈景帅
 *
 * 每天进步一点——2016年1月2日
 *
 */
public interface ServletResponse {
	public void setCharacterEncoding(String enc);

}
