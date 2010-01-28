package com.easymorse.videos.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootPanel;

public class Videos implements EntryPoint {

	@Override
	public void onModuleLoad() {
		HandlerManager eventBus = new HandlerManager(null);
		AppController appController = new AppController(eventBus, RootPanel
				.get());
		appController.go(RootPanel.get());
	}
}
