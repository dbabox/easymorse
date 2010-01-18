package com.easymorse.web;

import java.util.List;

public class Pagination {
	private List<Object> results;

	public List<Object> getResults() {
		return results;
	}

	public void setResults(List<Object> results) {
		this.results = results;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	private int pageNo;
}
