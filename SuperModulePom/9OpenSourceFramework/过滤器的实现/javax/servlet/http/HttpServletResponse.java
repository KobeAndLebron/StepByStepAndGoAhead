package javax.servlet.http;

import javax.servlet.ServletResponse;

/**
 * Extends ServletResponse to provide HTTP-specific functionality in sending a response.For example,
 * it has methods to access HTTP headers and cookies. 
 * 在Servlet中HttpServletResponse也是一个接口，这里为了简单。
 * @author 陈景帅
 *
 * 每天进步一点——2016年1月2日
 *
 */
public class HttpServletResponse implements ServletResponse{

	private String encoding;

	public String getCharacterEncoding() {
		return encoding;
	}

	public void setCharacterEncoding(String encoding) {
		this.encoding = encoding;
	}


}
