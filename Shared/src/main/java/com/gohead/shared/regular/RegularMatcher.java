package com.gohead.shared.regular;

import java.util.regex.Pattern;

public class RegularMatcher {
	private static final Pattern INT_PATTERN = Pattern.compile("^[1-9]*[0-9]*$");
	private static final Pattern BOOLEAN_PATTERN = Pattern.compile("^(true|false){1}$");
	
	private static boolean matches(Pattern pattern, String str){
		return pattern.matcher(str).matches();
	}
	
	public static boolean isInt(String str){
		return str == null ? false : matches(INT_PATTERN, str);
	}
	
	public static boolean isBoolean(String str){
		return str == null ? false : matches(BOOLEAN_PATTERN, str);
	}
}
