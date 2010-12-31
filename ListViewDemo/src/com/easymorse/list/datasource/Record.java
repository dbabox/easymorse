package com.easymorse.list.datasource;

/**
 * 记录
 * 
 * @author marshal
 * 
 */
public class Record {
	
	public static final String ORDER_BY_NAME="name";
	
	public static final String ORDER_BY_PLAY_TIMES="play_times";
	
	// id
	private Long id;

	private int playTimes;

	public int getPlayTimes() {
		return playTimes;
	}

	public void setPlayTimes(int playTimes) {
		this.playTimes = playTimes;
	}

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

	public Record(Long id, String name,int playTimes) {
		this.id = id;
		this.name = name;
		this.playTimes=playTimes;
	}

	public Record() {
	}
}
