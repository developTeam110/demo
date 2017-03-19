package com.demo.common.exception;

public class RedisException extends RuntimeException {

	private static final long serialVersionUID = -8543143152699181759L;

	private String message;

	public RedisException(String message, Object... paras){
		this.message = String.format(message, paras);
	}

	public RedisException(String message){
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
