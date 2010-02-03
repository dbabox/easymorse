package com.easymorse.videos.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface LoginSuccessEventHandler extends EventHandler {
	void onAuthenticationSuccess(LoginSuccessEvent event);
}
