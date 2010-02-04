package com.easymorse.videos.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface AccessDeniedEventHandler extends EventHandler {
	void onAccessDenied(AccessDeniedEvent event);
}
