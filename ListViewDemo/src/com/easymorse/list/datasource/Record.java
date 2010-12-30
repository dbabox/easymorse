package com.easymorse.list.datasource;

/**
 * 记录
 * 
 * @author marshal
 * 
 */
public class Record {
	// id
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// 名称
	private String name;

	public Record(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Record() {
	}
}
