package com.easymorse.videos.client.model;

import com.google.gwt.core.client.JavaScriptObject;

public class VideoItem extends JavaScriptObject {
	protected VideoItem() {
	}

	public final native String getId() /*-{
		return this.id;
	}-*/;

	public final native String getTitle() /*-{
		return this.title;
	}-*/;

	public final native String getContent() /*-{
		return this.content;
	}-*/;
	
	public static native VideoItem fromJson(String jsonString) /*-{
		return eval('(' + jsonString + ')');
	}-*/;

}
