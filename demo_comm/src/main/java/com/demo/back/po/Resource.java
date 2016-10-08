package com.demo.back.po;

import java.io.Serializable;
import java.util.Date;

public class Resource implements Serializable{

	private static final long serialVersionUID = 4516238789362123340L;

	/**
	 * 主键ID
	 */
	private Long id;

	/**
	 * 资源编码
	 */
	private String code;

	/**
	 * 资源名称
	 */
	private String name;

	/**
	 * URL资源描述
	 */
	private String desc;

	/**
	 * URL资源
	 */
	private String url;

	/**
	 * 级别
	 */
	private Integer level;

	/**
	 * 父ID
	 */
	private Long parentId;

	/**
	 * 排序值
	 */
	private Integer sort;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 资源的级别
	 */
	public static enum LEVEL {
		ONE(1, "一级"), TWO(2, "二级"), Three(3, "三级");

		private Integer value;
		private String cnName;

		LEVEL(Integer value, String cnName) {
			this.value = value;
			this.cnName = cnName;
		}

		public Integer getValue() {
			return value;
		}

		public String getCnName() {
			return this.cnName;
		}

		public String getCode() {
			return this.name();
		}

		public static String getCnName(String code) {
			for (LEVEL item : LEVEL.values()) {
				if (item.name().equals(code)) {
					return item.getCnName();
				}
			}
			return code;
		}

		public static Integer getValue(String code) {
			for (LEVEL item : LEVEL.values()) {
				if (item.getCode().equalsIgnoreCase(code)) {
					return item.getValue();
				}
			}
			return null;
		}

		@Override
		public String toString() {
			return this.name();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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
