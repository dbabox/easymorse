package com.easymorse.videos.client;

import com.easymorse.videos.client.event.AuthenticationSuccessEvent;
import com.easymorse.videos.client.event.AuthenticationSuccessEventHandler;
import com.easymorse.videos.client.event.LogOffEvent;
import com.easymorse.videos.client.event.LogOffEventHandler;
import com.easymorse.videos.client.presenter.LoginPresenter;
import com.easymorse.videos.client.presenter.Presenter;
import com.easymorse.videos.client.presenter.VideosPresenter;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;

public class AppController implements Presenter {

	private HandlerManager handlerManager;

	private HasWidgets container;

	public AppController(HandlerManager handlerManager, HasWidgets container) {
		this.handlerManager = handlerManager;
		this.container = container;
		this.bind();
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		new LoginPresenter(this.handlerManager).go(container);
	}

	private void bind() {
		this.handlerManager.addHandler(AuthenticationSuccessEvent.TYPE,
				new AuthenticationSuccessEventHandler() {

					@Override
					public void onAuthenticationSuccess(
							AuthenticationSuccessEvent event) {
						VideosPresenter videosPresenter = new VideosPresenter(
								handlerManager,container);
						videosPresenter.go(container);
					}
				});
		this.handlerManager.addHandler(LogOffEvent.TYPE,
				new LogOffEventHandler() {
					@Override
					public void onLogOff(LogOffEvent event) {
						go(container);
					}
				});
	}
}
