package com.easymorse.listview;

public class River {
	private int id;

	private String name;

	private int length;

	public River() {
	}

	public River(String name, int length) {
		this.name = name;
		this.length = length;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
}
