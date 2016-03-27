package javax.servlet.http;

import javax.servlet.ServletRequest;

/**
 *
 * Extends the ServletRequest to provide request information for HTTP servlets.
 * 在Servlet中HttpServletRequest也是一个接口，这里为了简单。
 * @author 陈景帅
 *
 * 每天进步一点——2016年1月2日
 *
 */
public class HttpServletRequest implements ServletRequest{
	private String encoding;

	public String getCharacterEncoding() {
		return encoding;
	}

	public void setCharacterEncoding(String encoding) {
		this.encoding = encoding;
	}
	
}
