package com.easymorse.videos.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class PlayMideaEvent extends GwtEvent<PlayMideaEventHandler> {
	
	public static Type<PlayMideaEventHandler> TYPE=new Type<PlayMideaEventHandler>();

	private String id;

	public PlayMideaEvent(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	@Override
	protected void dispatch(PlayMideaEventHandler handler) {
		handler.onPlayMidea(this);
	}

	@Override
	public Type<PlayMideaEventHandler> getAssociatedType() {
		return TYPE;
	}

}
