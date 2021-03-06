package com.demo.common.util;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.util.StringUtils;

@SuppressWarnings("deprecation")
public class JsonUtil {
	private static ObjectMapper mapper = new ObjectMapper();
	static {
		mapper.getSerializationConfig().setSerializationInclusion(Inclusion.NON_NULL);
		//设置输入时忽略JSON字符串中存在而Java对象实际没有的属性
		mapper.getDeserializationConfig().set(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public static String Object2Json(Object o) {
		if (o == null)
			return null;

		String s = null;

		try {
			s = mapper.writeValueAsString(o);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	public static <T> List<String> listObject2ListJson(List<T> objects) {
		if (objects == null)
			return null;

		List<String> lists = new ArrayList<String>();
		for (T t : objects) {
			lists.add(JsonUtil.Object2Json(t));
		}

		return lists;
	}

	public static <T> List<T> listJson2ListObject(List<String> jsons, Class<T> c) {
		if (jsons == null)
			return null;

		List<T> ts = new ArrayList<T>();
		for (String j : jsons) {
			ts.add(JsonUtil.Json2Object(j, c));
		}

		return ts;
	}

	public static <T> T Json2Object(String json, Class<T> c) {
		if (StringUtils.hasLength(json) == false)
			return null;

		T t = null;
		try {
			t = mapper.readValue(json, c);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	public static <T> T Json2Object(String json, TypeReference<T> tr) {
		if (StringUtils.hasLength(json) == false)
			return null;

		T t = null;
		try {
			t = (T) mapper.readValue(json, tr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (T) t;
	}
}
