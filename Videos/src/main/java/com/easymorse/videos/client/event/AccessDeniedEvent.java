package com.easymorse.videos.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AccessDeniedEvent extends GwtEvent<AccessDeniedEventHandler> {

	public static Type<AccessDeniedEventHandler> TYPE = new Type<AccessDeniedEventHandler>();

	@Override
	protected void dispatch(AccessDeniedEventHandler handler) {
		handler.onAccessDenied(this);
	}

	@Override
	public Type<AccessDeniedEventHandler> getAssociatedType() {
		return TYPE;
	}

}
