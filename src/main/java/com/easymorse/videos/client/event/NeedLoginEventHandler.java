package com.easymorse.videos.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface NeedLoginEventHandler extends EventHandler {
	void onNeedLogin(NeedLoginEvent event);
}
