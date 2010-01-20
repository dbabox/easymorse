package com.easymorse.weapons.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootPanel;

public class Weapons implements EntryPoint {

	@Override
	public void onModuleLoad() {
		HandlerManager eventBus = new HandlerManager(null);
		AppController appController=new AppController(eventBus);
		appController.go(RootPanel.get());
	}

}
