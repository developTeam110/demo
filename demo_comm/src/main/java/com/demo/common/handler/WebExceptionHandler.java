package com.demo.common.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.common.constant.ErrorCode;
import com.demo.common.vo.Result;

/** 
 * @Description:统一异常处理类 
 */
@ControllerAdvice
public class WebExceptionHandler {
	private final static Logger logger = LoggerFactory.getLogger(WebExceptionHandler.class);

	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public Object runtimeExHandler(RuntimeException ex, HttpServletRequest request, HttpServletResponse response) {
		//打印错误信息
		logger.error("occurs system error, caused by: ", ex);
		Result result = new Result();
		result.setErrorCode(ErrorCode.SYSTEM_INNER_ERROR);
		return result;
	}
}
