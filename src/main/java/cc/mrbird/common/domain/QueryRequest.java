package cc.mrbird.common.domain;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

public class QueryRequest implements Serializable {

	private static final long serialVersionUID = -4869594085374385813L;

	private int pageSize;
	private int pageNum;
	private String sort;
	private String sortOrder;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("pageSize", pageSize)
				.add("pageNum", pageNum)
				.add("sort", sort)
				.add("sortOrder", sortOrder)
				.toString();
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

}
