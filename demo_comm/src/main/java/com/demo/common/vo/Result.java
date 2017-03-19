package com.demo.common.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.demo.common.constant.ErrorCode;

public class Result implements Serializable {

	private static final long serialVersionUID = 2284484847624556651L;

	/**
	 * 返回的状态码
	 */
	private String code;

	/**
	 * 返回的提示信息
	 */
	private String message;

	/**
	 * 返回的数据
	 */
	private Object data;

	private Map<String, Object> dataMap;

	public Result() {
	}

	public Result(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public Result(Object data, String code, String message) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.code = errorCode.code().toString();
		this.message = errorCode.message();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void putDataMap(String key, Object value) {
		if (dataMap == null) {
			dataMap = new HashMap<String, Object>();
		}
		dataMap.put(key, value);
		this.data = dataMap;
	}

	@Override
	public String toString() {
		return "Result [code=" + code + ", message=" + message + ", data=" + data + "]";
	}
}
