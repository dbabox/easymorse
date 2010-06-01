package com.easymorse;

public class Content {
	private String id;

	private String title;
	
	private String url;
	
	private String widthUrl;
	
	private String heightUrl;

	public String getWidthUrl() {
		return widthUrl;
	}

	public void setWidthUrl(String widthUrl) {
		this.widthUrl = widthUrl;
	}

	public String getHeightUrl() {
		return heightUrl;
	}

	public void setHeightUrl(String heightUrl) {
		this.heightUrl = heightUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	private String comment;

	private String imageUrl;
}
