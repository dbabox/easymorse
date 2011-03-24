package com.easymorse.fragment;

public class Member {
	private String name;

	private String introduction;

	private int imageResourseId;

	private String blogUrl;

	private String microBlogUrl;

	public Member() {
	}

	public Member(String name, String introduction, int imageResourseId,
			String blogUrl) {
		super();
		this.name = name;
		this.introduction = introduction;
		this.imageResourseId = imageResourseId;
		this.blogUrl = blogUrl;
	}

	public Member(String name, String introduction, int imageResourseId,
			String blogUrl, String microBlogUrl) {
		super();
		this.name = name;
		this.introduction = introduction;
		this.imageResourseId = imageResourseId;
		this.blogUrl = blogUrl;
		this.microBlogUrl = microBlogUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public int getImageResourseId() {
		return imageResourseId;
	}

	public void setImageResourseId(int imageResourseId) {
		this.imageResourseId = imageResourseId;
	}

	public String getBlogUrl() {
		return blogUrl;
	}

	public void setBlogUrl(String blogUrl) {
		this.blogUrl = blogUrl;
	}

	public String getMicroBlogUrl() {
		return microBlogUrl;
	}

	public void setMicroBlogUrl(String microBlogUrl) {
		this.microBlogUrl = microBlogUrl;
	}
}
