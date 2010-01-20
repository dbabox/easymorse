package com.easymorse.weapons.client.model;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class Weapon extends JavaScriptObject {

	protected Weapon() {
	}

	public final native String getId() /*-{
		return this.id;
	}-*/;

	public final native void setId(String id) /*-{
		this.id = id;
	}-*/;

	public final native String getName() /*-{
		return this.name;
	}-*/;

	public final native void setName(String name) /*-{
		this.name = name;
	}-*/;

	public final native String getDescription() /*-{
		return this.description;
	}-*/;

	public final native void setDescription(String description) /*-{
		this.description = description;
	}-*/;

	public final native String getImageUrl() /*-{
		return this.imageUrl;
	}-*/;

	public final native void setImageUrl(String imageUrl) /*-{
		this.imageUrl = imageUrl;
	}-*/;

	public static native JsArray<Weapon> arrayFromJson(String jsonString) /*-{
		return eval('(' + jsonString + ')').data;
	}-*/;

}
