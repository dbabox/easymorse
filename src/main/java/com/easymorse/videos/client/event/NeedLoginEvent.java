package com.easymorse.videos.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class NeedLoginEvent extends GwtEvent<NeedLoginEventHandler> {

	public static Type<NeedLoginEventHandler> TYPE = new Type<NeedLoginEventHandler>();

	@Override
	protected void dispatch(NeedLoginEventHandler handler) {
		handler.onNeedLogin(this);
	}

	@Override
	public Type<NeedLoginEventHandler> getAssociatedType() {
		return TYPE;
	}

}
