package com.gohead.shared.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gohead.shared.collection.CollectionUtil;
import com.gohead.shared.json.JsonObjectConverter;
import com.gohead.shared.regular.RegularMatcher;

/**
 * Convert string to object.
 * 	Examples :
 * 		1. Basic types : ["a", 1, 2l, 2.2f, 3d] 基本数据类型产生的都是包装类
 * 		2. Collection : [<1, 2, 3>, [1, 2, 3], {"a" : 1, "b" : 2}] [] list <> set {} map
 * 		3. Object : ClassName : {"propertyName" : "propertyValue"},  {...} is a JSON string. 
 * 		4. Array : float[1, 2, 3] 数组也是包装类数组  Data[{}, {}] 会调用空的构造器
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
	
	private static final char START_LIST_NOTATION = '[';
	private static final char END_LIST_NOTATION = ']';
	private static final char START_SET_NOTATION = '<';
	private static final char END_SET_NOTATION = '>';
	private static final char START_MAP_NOTATION = '{';
	private static final char END_MAP_NOTATION = '}';
	
	public static <T> List<T> generateParameterList(String str, Class<T> componentType) {
		List<T> parameters = new ArrayList<>();
		if (str != null && !str.isEmpty()) {
			str = str.substring(str.indexOf(START_LIST_NOTATION) + 1, str.lastIndexOf(END_LIST_NOTATION));
			for (String parameterStr : new Str(str)) {
				parameterStr = parameterStr.trim();
				T obj = convertStrToObj(parameterStr, componentType);
				parameters.add(obj);
			}
		}
		return parameters;
	}
	
	public static <T> Set<T> generateParameterSet(String str, Class<T> componentType) {
		Set<T> parameters = new HashSet<>();
		if (str != null && !str.isEmpty()) {
			str = str.substring(str.indexOf(START_SET_NOTATION) + 1, str.lastIndexOf(END_SET_NOTATION));
			for (String parameterStr : new Str(str)) {
				parameterStr = parameterStr.trim();
				T obj = convertStrToObj(parameterStr, componentType);
				parameters.add(obj);
			}
		}
		return parameters;
	}
	
	public static <K, V> Map<K, V> generateParameterMap(String str, Class<K> keyType, Class<V> valueType) {
		Map<K, V> parameters = new HashMap<>();
		if (str != null && !str.isEmpty()) {
			str = str.substring(str.indexOf(START_MAP_NOTATION) + 1, str.lastIndexOf(END_MAP_NOTATION));
			for (String parameterStr : new Str(str)) {
				parameterStr = parameterStr.trim();
				String[] entry = str.split(":");
				String key = entry[0].trim();
				String value = entry[1].trim();
				parameters.put(convertStrToObj(key, keyType), convertStrToObj(value, valueType));
			}
		}
		return parameters;
	}
	
	/**
	 * int  [ 1, 2, 3, 4]  
	 * @param str
	 * @param componentType
	 * @return
	 */
	public static <T> T[] generateParameterArray(String str, Class<T> componentType) {
		// All of elements type is T for this method,so next operation do not throw ClassCastException.
		List<T> list = (List<T>) generateParameterList(str, componentType);  
		T[] arrT = CollectionUtil.convertCollectionToArray(list, componentType);
		return arrT;
	}
	
	private static Class<?> getClassByAlias(String alias){
		Map<String, Class<?>> aliasClassMap = new HashMap<>();
		aliasClassMap.put("int", Integer.class);
		aliasClassMap.put("float", Float.class);
		aliasClassMap.put("double", Double.class);
		aliasClassMap.put("long", Long.class);
		aliasClassMap.put("boolean", Boolean.class);
		Class<?> clazz = aliasClassMap.get(alias);
		try {
			clazz = Class.forName(alias);
		} catch (ClassNotFoundException e) {
			LOGGER.debug("Non-exist class for " + alias);
		}
		return clazz;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T convertStrToObj(String str, Class<T> classType) {
		Object obj = null;
		if (str != null) {
			if (classType == String.class || str.startsWith("\"") && str.endsWith("\"")) { // String
				obj = str;
			} else if (classType == Long.class || str.toLowerCase().endsWith("l")) { // long
				str = str.replace("l", "");
				obj = Long.parseLong(str);
			} else if (classType == Float.class || str.toLowerCase().endsWith("f")) { // float
				str = str.replace("f", "");
				obj = Float.parseFloat(str);
			} else if (classType == Double.class || str.toLowerCase().endsWith("d")) { // double
				str = str.replace("d", "");
				obj = Double.parseDouble(str);
			} else if (classType == Integer.class || RegularMatcher.isInt(str)) { // int
				obj = Integer.parseInt(str);
			} else if (classType == Boolean.class || RegularMatcher.isBoolean(str)) { // boolean
				obj = Boolean.parseBoolean(str);
			} else if (judgeType(str, START_LIST_NOTATION, END_LIST_NOTATION)){ // list
				obj = generateParameterList(str, Object.class);
			} else if (judgeType(str, START_SET_NOTATION, END_SET_NOTATION)){ // set
				obj = generateParameterSet(str, Object.class);
			} else if (judgeType(str, START_MAP_NOTATION, END_MAP_NOTATION)){ // map
				if(classType == Object.class){ // general map
					obj = generateParameterMap(str, Object.class, Object.class);
				}else{ // jsonObject {}
					obj = JsonObjectConverter.getObjByJsonStrAndClass(str, classType);
				}
			}else if (RegularMatcher.isArray(str)){
				String classAlias = str.substring(0, str.indexOf("[")).trim();
				String eleStr = str.substring(str.indexOf("[")).trim();
				obj = generateParameterArray(eleStr, getClassByAlias(classAlias));
			} else { // object java.lang.Object : {}
				int separatorIndex = str.indexOf(":");
				String className = str.substring(0, separatorIndex).trim();
				String jsonMap = str.substring(separatorIndex + 1).trim();
				obj = JsonObjectConverter.getObjByJsonStrAndClassName(jsonMap, className);
			}
		}
		T t = null;
		try{
			if(classType != Object.class && classType != obj.getClass()){
				throw new ClassCastException();
			}
			t = (T) obj;
		}catch (ClassCastException e){
			throw new RuntimeException("Wrong type : " + obj.getClass().getName() + " for " + classType.getName());
		}
		return t;
	}
	
	private static boolean judgeType(String str, char startChar, char endChar){
		StringBuilder sb = new StringBuilder();
		StringBuilder sb1 = new StringBuilder();
		sb.append(startChar);
		sb1.append(endChar);
		return judgeType(str, sb.toString(), sb1.toString());
	}
	
	private static boolean judgeType(String str, String startStr, String endStr){
		if(str != null){
			return (str.indexOf(startStr) == 0 && str.lastIndexOf(endStr) == str.length() - 1);
		}
		return false;
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
				boolean isString = false;
				int temp = index;
				for(;index < str.length(); index++){
					char character = str.charAt(index);
					if(index == temp){
						isString = character == '"' ? true : false;
					}
					if(!isString && (character == '[' || character == '<' || character == '{')){
						i++;
						isFlag = true;
					}
					
					if(!isString && (character == ']' || character == '>' || character == '}')){
						i--;
					}
					if(!isFlag){
						if(isString || character != VARIABLE_SEPARATOR){
							sb.append(character);
						}else{
							index++;
							break;
						}
					}else{
						sb.append(character);
						if(i == 0){ // ], or ]] or ]> or ]} or }, 1
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
