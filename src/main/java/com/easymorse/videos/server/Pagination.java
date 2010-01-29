package com.easymorse.videos.server;

import java.util.List;

public class Pagination<T,C> {
	private int no;
	
	public C getCondition() {
		return condition;
	}

	public void setCondition(C condition) {
		this.condition = condition;
	}

	private C condition;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	private int count;

	public int getNo() {

		if (count < no * size) {
			return last();
		}

		return no;
	}

	public int last() {
		return (int) Math.ceil((float) count / size);
	}

	public int first() {
		return 1;
	}

	public boolean hasNext() {
		return false;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

	private int size;

	private List<T> results;
}
