package com.easymorse.videos.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class LogOffEvent extends GwtEvent<LogOffEventHandler> {

	public static Type<LogOffEventHandler> TYPE = new Type<LogOffEventHandler>();

	@Override
	protected void dispatch(LogOffEventHandler handler) {
		handler.onLogOff(this);
	}

	@Override
	public Type<LogOffEventHandler> getAssociatedType() {
		return TYPE;
	}

}
