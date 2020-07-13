package com.chinahuik.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtil {

	public static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

	private JsonUtil() {}

	private static ObjectMapper objectMapper;

	static {
		objectMapper = new JsonObjectMapper();
		objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		objectMapper.setDateFormat(new SimpleDateFormat("yyyyMMddHHmmss"));
	}

	public static <T> String toJson(T obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			LOGGER.error("class: {} serialize to JSON error: {}", ToStringBuilder.reflectionToString(obj), e);
		}
		return "{}";
	}

	public static <T> T fromJson(String json, Class<T> cls) {
		try {
			if (StringUtils.isBlank(json)) {
				return null;
			}
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return objectMapper.readValue(json, cls);
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error("deserialize failed! JSON string: {}, class: {}, reason: {}", json, cls, e);
		}
		return null;
	}

	public static <E, T extends Collection<E>> T fromJson(String json, Class<E> cls, Class<T> collectionCls) {
		if (StringUtils.isBlank(json)) {
			return null;
		}
		JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionCls, cls);
		try {
			return objectMapper.readValue(json, javaType);
		} catch (IOException e) {
			e.printStackTrace();
			/*LOGGER.error("deserialize failed! JSON string: {}, Object class:{}, Collection class: {}, reason: {}",
					json, cls, collectionCls, e);*/
		}
		return null;
	}
}
