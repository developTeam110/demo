package com.demo.common.exception;

import com.demo.common.constant.ErrorCode;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -4858786785999370898L;

	private String code;
	private String message;
	private ErrorCode errorCode;

	public BusinessException() {
		super();
	}

	public BusinessException(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public BusinessException(String message) {
		this.message = message;
	}

	public BusinessException(ErrorCode errorCode) {
		this.errorCode = errorCode;
		this.code = errorCode.code().toString();
		this.message = errorCode.message();
	}

	public String getCode() {
		return this.code;
	}

	public String getMessage() {
		return this.message;
	}

	public ErrorCode getErrorCode() {
		return this.errorCode;
	}
}
