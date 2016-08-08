package com.gohead.shared.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gohead.shared.regular.RegularMatcher;

/**
 * Convert string to object.
 * 	Examples :
 * 		1. Basic types : ["a", 1, 2l, 2.2f, 3d] {@linkplain #generateParameterList(String)}
 * 		2. Collection : [<1, 2, 3>, [1, 2, 3], {"a" : 1, "b" : 2}] {@linkplain #generateParameterList(String)}
 * 		3. Object : ClassName : {"propertyName" : "propertyValue"},  {...} is a JSON string. {@linkplain #convertStrToObj(String)}
 * 
 * @author ChenJingShuai 8 Aug 2016
 *
 */
public class ConvertStrToObj {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConvertStrToObj.class);
	
	/**
	 * Separator of variables.
	 */
	private static final char VARIABLE_SEPARATOR = ',';
	
	public static List<Object> generateParameterList(String str) {
		List<Object> parameters = new ArrayList<>();
		if (str != null && !str.isEmpty()) {
			str = str.substring(str.indexOf("[") + 1, str.lastIndexOf("]"));
			for (String parameterStr : new Str(str)) {
				parameterStr = parameterStr.trim();
				Object obj = convertStrToObj(parameterStr);
				parameters.add(obj);
			}
		}
		return parameters;
	}
	
	public static Set<Object> generateParameterSet(String str) {
		Set<Object> parameters = new HashSet<>();
		if (str != null && !str.isEmpty()) {
			str = str.substring(str.indexOf("<") + 1, str.lastIndexOf(">"));
			for (String parameterStr : new Str(str)) {
				parameterStr = parameterStr.trim();
				Object obj = convertStrToObj(parameterStr);
				parameters.add(obj);
			}
		}
		return parameters;
	}
	
	public static Map<Object, Object> generateParameterMap(String str) {
		Map<Object, Object> parameters = new HashMap<>();
		if (str != null && !str.isEmpty()) {
			str = str.substring(str.indexOf("{") + 1, str.lastIndexOf("}"));
			for (String parameterStr : new Str(str)) {
				parameterStr = parameterStr.trim();
				String[] entry = str.split(":");
				String key = entry[0].trim();
				String value = entry[1].trim();
				parameters.put(convertStrToObj(key), convertStrToObj(value));
			}
		}
		return parameters;
	}

	public static Object convertStrToObj(String str) {
		Object obj = null;
		if (str != null) {
			if (str.startsWith("\"") && str.endsWith("\"")) { // String
				obj = str;
			} else if (str.toLowerCase().endsWith("l")) { // long
				str = str.replace("l", "");
				obj = Long.parseLong(str);
			} else if (str.toLowerCase().endsWith("f")) { // float
				str = str.replace("f", "");
				obj = Float.parseFloat(str);
			} else if (str.toLowerCase().endsWith("d")) { // double
				str = str.replace("d", "");
				obj = Double.parseDouble(str);
			} else if (RegularMatcher.isInt(str)) { // int
				obj = Integer.parseInt(str);
			} else if (RegularMatcher.isBoolean(str)) { // boolean
				obj = Boolean.parseBoolean(str);
			} else if (str.startsWith("[") && str.endsWith("]")){ // list
				obj = generateParameterList(str);
			} else if (str.startsWith("<") && str.endsWith(">")){ // set
				obj = generateParameterSet(str);
			} else if (str.startsWith("{") && str.endsWith("}")){ // map
				obj = generateParameterMap(str);
			}else{ // object
				int separatorIndex = str.indexOf(":");
				String className = str.substring(0, separatorIndex).trim();
				String jsonMap = str.substring(separatorIndex + 1).trim();
				try {
					ObjectMapper jsonMapper = new ObjectMapper();
					Class<?> clazz = Class.forName(className);
					try {
						obj = jsonMapper.readValue(jsonMap, clazz);
					} catch (JsonParseException e) {
						e.printStackTrace();
					} catch (JsonMappingException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						// 没有需要关闭的资源
					}
				} catch (ClassNotFoundException e) {
					LOGGER.debug("Non-exist class for " + className);
					e.printStackTrace();
				}
			}
		}
		return obj;
	}
	
	static class Str implements Iterable<String>{
		private String str;
		
		public Str(String str) {
			this.str = str;
		}
		
		@Override
		public Iterator<String> iterator() {
			return new StrIterator();
		}
		
		/**
		 * String iterator to get next value
		 * 
		 * @author ChenJingShuai 8 Aug 2016
		 *
		 */
		class StrIterator implements Iterator<String>{
			private int index = 0;
			
			@Override
			public boolean hasNext() {
				return index < str.length();
			}

			@Override
			public String next() {
				StringBuilder sb = new StringBuilder();
				int i = 0;
				boolean isFlag = false;
				for(;index < str.length(); index++){
					char character = str.charAt(index);
					if(character == '[' || character == '<' || character == '{'){
						i++;
						isFlag = true;
					}
					
					if(character == ']' || character == '>' || character == '}'){
						i--;
					}
					if(!isFlag){
						if(character != VARIABLE_SEPARATOR){
							sb.append(character);
						}else{
							index++;
							break;
						}
					}else{
						sb.append(character);
						if(i == 0){ // ], or ]] or ]> or ]}
							index++;
							index++;
							break;
						}
					}
				}
				return sb.toString();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
			
		}
	}
}	
