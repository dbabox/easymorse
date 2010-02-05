package com.easymorse.videos.client;

import com.easymorse.videos.client.event.AccessDeniedEvent;
import com.easymorse.videos.client.event.AccessDeniedEventHandler;
import com.easymorse.videos.client.event.LogOffEvent;
import com.easymorse.videos.client.event.LogOffEventHandler;
import com.easymorse.videos.client.event.LoginSuccessEvent;
import com.easymorse.videos.client.event.LoginSuccessEventHandler;
import com.easymorse.videos.client.event.NeedLoginEvent;
import com.easymorse.videos.client.event.NeedLoginEventHandler;
import com.easymorse.videos.client.presenter.LoginPresenter;
import com.easymorse.videos.client.presenter.Presenter;
import com.easymorse.videos.client.presenter.VideosPresenter;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AppController implements Presenter {

	private HandlerManager handlerManager;

	private HasWidgets container;

	private VideosPresenter videosPresenter;

	@SuppressWarnings("unused")
	private String userName;

	public AppController(HandlerManager handlerManager, HasWidgets container) {
		this.handlerManager = handlerManager;
		this.container = container;
		this.bind();
		this.videosPresenter = new VideosPresenter(this.handlerManager,
				container);
	}

	@Override
	public void go(HasWidgets container) {
		checkLogin();
	}

	private void checkLogin() {
		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET,
				"../isLogined.json");
		requestBuilder.setCallback(new RequestCallback() {

			@Override
			public void onResponseReceived(Request request, Response response) {
				if (response.getStatusCode() == 401) {
					handlerManager.fireEvent(new NeedLoginEvent());
				} else {
					container.clear();
					videosPresenter.go(container);
				}
			}

			@Override
			public void onError(Request request, Throwable e) {
				Window.alert("error!");
			}
		});
		
		try {
			requestBuilder.send();
		} catch (RequestException e) {
			e.printStackTrace();
		}
	}

	private void bind() {
		this.handlerManager.addHandler(LoginSuccessEvent.TYPE,
				new LoginSuccessEventHandler() {

					@Override
					public void onAuthenticationSuccess(LoginSuccessEvent event) {
						userName = event.getUserName();
						go(container);
					}
				});
		this.handlerManager.addHandler(LogOffEvent.TYPE,
				new LogOffEventHandler() {
					@Override
					public void onLogOff(LogOffEvent event) {
						RequestBuilder builder = new RequestBuilder(
								RequestBuilder.GET,
								"../j_spring_security_logout");
						builder.setCallback(new RequestCallback() {

							@Override
							public void onResponseReceived(Request request,
									Response response) {
								if (response.getStatusCode() < 400) {
									Window.Location.reload();
								}
							}

							@Override
							public void onError(Request request, Throwable e) {
								Window.alert("error");
							}
						});

						try {
							builder.send();
						} catch (RequestException e) {
							e.printStackTrace();
						}
					}
				});
		this.handlerManager.addHandler(NeedLoginEvent.TYPE,
				new NeedLoginEventHandler() {

					@Override
					public void onNeedLogin(NeedLoginEvent event) {
						new LoginPresenter(handlerManager).go(container);
					}
				});
		this.handlerManager.addHandler(AccessDeniedEvent.TYPE,
				new AccessDeniedEventHandler() {
					@Override
					public void onAccessDenied(AccessDeniedEvent event) {
						final DialogBox box = new DialogBox();
						box.setText("提示");
						box.setAnimationEnabled(true);
						box.setGlassEnabled(true);

						VerticalPanel panel = new VerticalPanel();
						panel.add(new HTML("当前用户没有操作权限，可点击退出，然后用更高权限用户登录。"));
						Button closeButton = new Button("关闭");
						panel.add(closeButton);
						closeButton.addClickHandler(new ClickHandler() {
							@Override
							public void onClick(ClickEvent event) {
								box.hide();
							}
						});
						box.add(panel);
						box.center();
						box.show();
					}
				});

	}
}
