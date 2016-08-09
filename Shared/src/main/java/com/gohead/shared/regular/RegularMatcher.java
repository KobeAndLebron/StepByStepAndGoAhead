package com.gohead.shared.regular;

import java.util.regex.Pattern;

public class RegularMatcher {
	private static final Pattern INT_PATTERN = Pattern.compile("^[1-9]*[0-9]*$");
	private static final Pattern BOOLEAN_PATTERN = Pattern.compile("^(true|false){1}$");
	private static final Pattern ARRAY_PATTERN = Pattern.compile("^[a-zA-Z\\.]+[ ]*\\[[\\s\\S]+\\][ ]*$");
	
	private static boolean matches(Pattern pattern, String str){
		return pattern.matcher(str).matches();
	}
	
	public static boolean isInt(String str){
		return str == null ? false : matches(INT_PATTERN, str);
	}
	
	public static boolean isBoolean(String str){
		return str == null ? false : matches(BOOLEAN_PATTERN, str);
	}
	
	/**
	 * String is array? 
	 * 	examples: float[ 1, 2, 3 ]
	 * 	wrong examples: [] float1,2,3] float[1,2 float1,2
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isArray(String str){
		return str == null ? false : matches(ARRAY_PATTERN, str);
	}
}
