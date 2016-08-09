package com.gohead.shared.json;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonObjectConverter {
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonObjectConverter.class);
	private static final ObjectMapper jsonMapper = new ObjectMapper();
	
	public static Object getObjByJsonStrAndClassName(String jsonStr, String className){
		Object obj = null;
		try {
			Class<?> clazz = Class.forName(className);
			obj = getObjByJsonStrAndClass(jsonStr, clazz);
		} catch (ClassNotFoundException e) {
			LOGGER.debug("Non-exist class for " + className);
			e.printStackTrace();
		}
		return obj;
	}
	
	public static <T> T getObjByJsonStrAndClass(String jsonStr, Class<T> clazz){
		T obj = null;
		try {
			obj = jsonMapper.readValue(jsonStr, clazz);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 没有需要关闭的资源
		}
		return obj;
	}
}
