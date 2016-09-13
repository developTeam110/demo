package com.demo.common.constant;

import java.io.Serializable;

/** 
* REVIEW
* @Description: 公共枚举类
* @author jingkun.wang@baidao.com
* @date 2016年7月2日 下午3:03:41
* 
*/
public class CommEnum implements Serializable {

	private static final long serialVersionUID = 4591463587224394601L;

	/**
	 * 常用的时间秒数
	 */
	public static enum TIME_SECONDS {

		/**
		 * 一天
		 */
		ONE_DAY(60 * 60 * 24),

		/**
		 * 一周
		 */
		ONE_WEEK(60 * 60 * 24 * 7),

		/**
		 * 一个月
		 */
		ONE_MONTH(60 * 60 * 24 * 30);

		private Integer sec;

		TIME_SECONDS(Integer sec) {
			this.sec = sec;
		}

		public Integer sec() {
			return this.sec;
		}

		@Override
		public String toString() {
			return this.name();
		}
	}

	/** 
	 * 是否判断
	 */
	public static enum YES_OR_NO {

		/**
		 * 是
		 */
		YES(1, "是"),

		/**
		 * 不是
		 */
		NO(0, "否");

		private Integer code;
		private String cnName;

		YES_OR_NO(Integer code, String cnName) {
			this.code = code;
			this.cnName = cnName;
		}

		public Integer code() {
			return this.code;
		}

		public Integer getCode() {
			return this.code;
		}

		public String cnName() {
			return this.cnName;
		}

		public String getCnName() {
			return this.cnName;
		}

		@Override
		public String toString() {
			return this.name();
		}
	}
}
