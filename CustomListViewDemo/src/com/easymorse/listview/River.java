package com.easymorse.listview;

public class River {
	private int id;

	private String name;

	private String introduction;

	private int length;

	private String imageUrl;

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public River() {
	}

	public River(String name, int length, String introduction, String imageUrl) {
		this.name = name;
		this.length = length;
		this.introduction = introduction;
		this.imageUrl = imageUrl;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
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
