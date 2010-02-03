package com.easymorse.videos.client.model;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class VideoItemPagination extends JavaScriptObject {
	protected VideoItemPagination() {
	}

	public final native JsArray<VideoItem> getResults() /*-{
		return this.results;
	}-*/;

	public final native boolean isNext() /*-{
		return this.next;
	}-*/;

	public final native boolean isPrevious() /*-{
		return this.previous;
	}-*/;

	public final native int getNo() /*-{
		return this.no;
	}-*/;

	public static native VideoItemPagination fromJson(String jsonString) /*-{
		return eval('(' + jsonString + ')').pagination;
	}-*/;
}
