package com.demo.common.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesUtil {

	private final static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

	private static Properties props;

	/**
	 * 加载指定的多个资源文件。
	 *
	 * @param filename
	 */
	public static void loadPropertyFiles(List<String> filenames) {
		props = new Properties();
		InputStream in = null;
		try {
			if (filenames != null) {
				for (String filename : filenames) {
					in = PropertiesUtil.class.getClassLoader().getResourceAsStream(filename);
					props.load(in);
				}
			}
		} catch (Exception ex) {
			logger.error("Fail to load system initialize rules file.", ex);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (java.io.IOException e) {
					logger.error("I/O Exception at close InputStream.");
				}
			}
		}
	}

	public static void modifyProperty(String key,String value){
		props.setProperty(key, value);
	}

	/**
	 * Get the value in system property file by the key
	 *
	 * @param key
	 *            key String
	 * @return String
	 */
	public static String getProperty(String key) {
		if(props == null) return null;

		Object obj = props.get(key);
		if (obj == null) {
			return null;
		} else {
			return obj.toString();
		}
	}

	public static String getProperty(String key, String defaultValue) {
		if (props == null)
			return defaultValue;
		Object obj = props.get(key);
		if (obj != null) {
			return obj.toString();
		} else {
			return defaultValue;
		}
	}

	public static Map<String,String> getAllProperties(){
		Map<String,String> map = new HashMap<String,String>();
		Iterator<Entry<Object, Object>> it = props.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<Object, Object> entry= (Map.Entry<Object, Object>)it.next();
			String key = entry.getKey().toString();
			String value = entry.getValue().toString();
			map.put(key, value);
		}
		return map;
	}

	public static Boolean getBooleanProperty(String key) {
		String v = getProperty(key);
		Boolean result = Boolean.valueOf(v);
		return result;
	}

	public static Long getLongProperty(String key) {
		String v = getProperty(key);
		if (v == null) {
			return null;
		}
		return Long.valueOf(v);
	}

	public static Integer getIntegerProperty(String key) {
		String v = getProperty(key);

		if (v == null) {
			return null;
		}

		return Integer.valueOf(v);
	}

}
