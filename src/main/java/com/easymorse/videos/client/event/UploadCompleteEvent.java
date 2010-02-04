package com.easymorse.videos.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class UploadCompleteEvent extends GwtEvent<UploadCompleteEventHandler> {

	public static Type<UploadCompleteEventHandler> TYPE = new Type<UploadCompleteEventHandler>();

	@Override
	protected void dispatch(UploadCompleteEventHandler eventHandler) {
		eventHandler.onUploadComplete(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<UploadCompleteEventHandler> getAssociatedType() {
		return TYPE;
	}

}
