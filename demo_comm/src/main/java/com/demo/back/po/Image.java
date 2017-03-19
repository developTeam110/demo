package com.demo.back.po;

import java.io.Serializable;
import java.util.Date;


public class Image implements Serializable{

	private static final long serialVersionUID = -6253134032620227109L;

	/**
	 * 主键ID
	 */
	private Long id;

	/**
	 * 路径
	 */
	private String path;

	/**
	 * 类型
	 */
	private Integer type;

	/**
	 * 状态 {@link STATUS}}
	 */
	private Integer status;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 状态枚举
	 */
	public static enum STATUS {

		NORMAL(1, "正常"),

		DELETE(2, "已删除");

		private Integer code;

		private String cnName;

		STATUS(Integer code, String cnName) {
			this.code = code;
			this.cnName = cnName;
		}

		public Integer code() {
			return this.code;
		}

		public String cnName() {
			return this.cnName;
		}

		public String getCnNameByCode(Integer code) {
			for (STATUS item : STATUS.values()) {
				if (item.code.equals(code)) {
					return item.cnName;
				}
			}

			return code == null ? null : code.toString();
		}

		public boolean isValidCode(Integer code) {
			for (STATUS item : STATUS.values()) {
				if (item.code.equals(code)) {
					return true;
				}
			}

			return false;
		}

		@Override
		public String toString(){
			return this.name();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
