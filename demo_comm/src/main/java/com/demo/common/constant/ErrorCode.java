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

	//（1）10001~10100 全局参数错误
	PARAM_IS_BLANK(10001, "参数为空"),
	PARAM_IS_INVALID(10002, "参数无效"),
	PARAM_NO_SELECTED_ITEM(10003, "没有指定任意选中项"),

	//（2）10101~10200 为空错误
	PARAM_LOGIN_NAME_NOT_EMPTY(10101, "登录名不能为空"),
	PARAM_PASSWORD_NOT_EMPTY(100102, "密码不能为空"),
	PARAM_NICKNAME_NOT_EMPTY(100103, "昵称不能为空"),
	PARAM_CODE_NOT_EMPTY(100104, "编码不能为空"),
	PARAM_NAME_NOT_EMPTY(100105, "名称不能为空"),

	//（3）10201~10300 格式错误
	PARAM_LOGIN_NAME_INVALID(10201, "登录名格式不正确"),
	PARAM_PASSWORD_INVALID(10202, "密码格式不正确"),
	PARAM_EMAIL_INVALID(10203, "邮箱格式不正确"),
	PARAM_PHONE_INVALID(10204, "无效的手机号码"),
	PARAM_IMAGE_SUFFIX_INVALID(10205, "图片格式不允许"),

	//（4）10301~10400 已存在错误
	PARAM_LOGIN_NAME_IS_EXISTED(10301, "登录名已经存在"),
	PARAM_EMAIL_IS_EXISTED(10302, "邮箱已经存在"),
	PARAM_PHONE_IS_EXISTED(10302, "电话号已经存在"),

	//（5）10401~19999 其他参数错误 
	PARAM_LOGIN_NAME_OR_PWD_ERROR(10401, "登录名或密码错误"),

	/* 用户错误：20001-29999 */
	USER_NOT_EXIST(20001, "用户不存在"),
	USER_NOT_LOGIN(20002, "用户未登录"),
	USER_IS_NOT_INNER(20003, "不是内部账户"),
	USER_IS_FREEZE(20004, "账户已被冻结"),

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