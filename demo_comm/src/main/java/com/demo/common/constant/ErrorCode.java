package com.demo.common.constant;

import java.util.ArrayList;
import java.util.List;

/** 
* REVIEW
* @Description: api返回状态码
* @author jingkun.wang@baidao.com
* @date 2016年6月1日 上午10:22:19 
* 
*/
public enum ErrorCode {

	SUCCESS(1, "成功"),

	/* 参数错误：10001-19999 */

	/* 用户错误：20001-29999 */

	/* 业务错误：30001-39999 */


	//30201 ~ 30300 产品商品相关

	/* 系统错误：40001-49999 */
	SYSTEM_INNER_ERROR(40001, "系统繁忙，请稍后重试"), SYSTEM_500_EXCEPTION(40002, "服务器500异常，请联系开发人员"), SYSTEM_REPEAT_EXCEPTION(40003, "同一异常高频出现"),

	/*数据错误：50001 - 599999*/
	RESULE_DATA_NONE(50001, "查无数据"),

	/* 接口错误：60001 - 69999*/
	INTERFACE_INNER_INVOKE_ERROR(60001, "内部系统接口调用异常"), INTERFACE_OUTTER_INVOKE_ERROR(60002, "外部系统接口调用异常"), INTERFACE_FORBID_VISIT(60003, "该接口禁止访问"), INTERFACE_ADDRESS_INVALID(60004, "接口地址无效"), INTERFACE_REQUEST_TIMEOUT(
			60005, "接口请求超时");

	private Integer code;
	private String message;

	ErrorCode(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer code() {
		return this.code;
	}

	public String message() {
		return this.message;
	}

	public static String getMessage(String name) {
		for (ErrorCode item : ErrorCode.values()) {
			if (item.name().equals(name)) {
				return item.message;
			}
		}
		return name;
	}

	public static Integer getCode(String name) {
		for (ErrorCode item : ErrorCode.values()) {
			if (item.name().equals(name)) {
				return item.code;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return this.name();
	}

	//校验重复的code 值
	public static void main(String[] args) {
		ErrorCode[] errorCodes = ErrorCode.values();
		List<Integer> codeList = new ArrayList<Integer>();
		for (ErrorCode errorCode : errorCodes) {
			if (codeList.contains(errorCode.code)) {
				System.out.println(errorCode.code);
			} else {
				codeList.add(errorCode.code());
			}
		}
	}
}