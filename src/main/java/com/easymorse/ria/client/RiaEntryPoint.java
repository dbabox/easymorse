package com.easymorse.ria.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * RIA应用程序的入口。
 * 
 * @author marshal
 * 
 */
public class RiaEntryPoint implements EntryPoint {

	@Override
	public void onModuleLoad() {
		new ApplicationController(new HandlerManager(null)).go(RootPanel.get());
	}

}
