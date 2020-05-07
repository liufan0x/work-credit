package com.anjbo.utils.huarong;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/**
 * Json工具类
 * @author chenzm
 * @since 2017年8月22日
 */
public class JsonUtils {
	private static volatile ObjectMapper mapper = new ObjectMapper();

	private JsonUtils() {};

	public static ObjectMapper getInstance() {
		return mapper;
	}

	public static byte[] toJsonBytes(Object obj) {
		try {
			return mapper.writeValueAsBytes(obj);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String toJsonString(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T toObject(String json, Class<T> clazz) {
		if (json == null) return null;
		try {
			return mapper.readValue(json, clazz);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T toObject(String json, TypeReference<T> typeReference) {
		if (json == null) return null;
		try {
			return mapper.readValue(json, typeReference);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T toObject(byte[] json, Class<T> clazz) {
		if (json == null) return null;
		try {
			return mapper.readValue(json, clazz);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T toObject(byte[] json, TypeReference<T> typeReference) {
		if (json == null) return null;
		try {
			return mapper.readValue(json, typeReference);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T toJsonToObject(Object from, Class<T> clazz) {
		return JsonUtils.toObject(JsonUtils.toJsonString(from), clazz);
	}

}
