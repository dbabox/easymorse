package com.easymorse.list.datasource;

import java.util.List;

/**
 * 分页对象
 * 
 * @author marshal
 * 
 */
public class Page {
	private List<Record> results;

	private int count;

	private int no;

	private int size;

	/**
	 * 
	 * @param no
	 *            页号
	 * @param size
	 *            页大小
	 */
	public Page(int no, int size) {
		this.no = no;
		this.size = size;
	}

	public Page() {
	}

	/**
	 * 页面总数
	 * 
	 * @return 页面总数
	 */
	public int getPageCount() {
		return (int) Math.ceil(((double) count) / size);
	}

	/**
	 * 页面结果列表
	 * 
	 * @return 页面结果列表
	 */
	public List<Record> getResults() {
		return results;
	}

	/**
	 * 
	 * @param results
	 */
	public void setResults(List<Record> results) {
		this.results = results;
	}

	/**
	 * 
	 * @return 结果总数，全部的，不只是本页的。
	 */
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * 页号
	 * 
	 * @return 页号
	 */
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	/**
	 * 页显示结果大小
	 * 
	 * @return
	 */
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
