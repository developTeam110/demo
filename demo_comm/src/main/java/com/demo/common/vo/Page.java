package com.demo.common.vo;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页类
 */
public class Page<T> implements Serializable {

	private static final long serialVersionUID = -2966095155464071413L;

	/**
	 * 偏移量
	 */
	private long offset = 0;

	/**
	 * 每页数查询数量，默认10条
	 */
	private long limit = 10;

	/**
	 * 记录总数
	 */
	private long total = 0;

	/**
	 * 排序的字段名
	 */
	private String sort;

	/**
	 * 排序方式（asc，desc）
	 */
	private String order;

	/**
	 * 结果集
	 */
	private List<T> rows = Collections.emptyList();

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public long getLimit() {
		return limit;
	}

	public void setLimit(long limit) {
		this.limit = limit;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}
