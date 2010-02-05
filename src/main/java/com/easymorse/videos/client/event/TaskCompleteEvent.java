package com.easymorse.videos.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class TaskCompleteEvent extends GwtEvent<TaskCompleteEventHandler> {

	public static Type<TaskCompleteEventHandler> TYPE=new Type<TaskCompleteEventHandler>();
	
	@Override
	protected void dispatch(TaskCompleteEventHandler eventHandler) {
		eventHandler.onTaskComplete(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<TaskCompleteEventHandler> getAssociatedType() {
		return TYPE;
	}

}
