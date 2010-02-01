package com.easymorse.videos.client.presenter;

import com.easymorse.videos.client.event.AccessDeniedEvent;
import com.easymorse.videos.client.event.LogOffEvent;
import com.easymorse.videos.client.event.NeedLoginEvent;
import com.easymorse.videos.client.model.VideoItem;
import com.easymorse.videos.client.view.VideoItemView;
import com.easymorse.videos.client.view.VideosView;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;

public class VideosPresenter implements Presenter, ValueChangeHandler<String> {

	private HandlerManager handlerManager;

	private VideosView videosView;

	private HasWidgets container;

	public VideosPresenter(HandlerManager handlerManager, HasWidgets container) {
		this.handlerManager = handlerManager;
		this.container = container;
		this.videosView = new VideosView();
		bind();
	}

	private void bind() {
		
		Window.addResizeHandler(this.videosView);
		
		History.addValueChangeHandler(this);

		this.videosView.getLogoffButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				videosView.getLogoffDialogBox().hide();
				handlerManager.fireEvent(new LogOffEvent());
			}
		});

		this.videosView.getTabPanel().getTabBar().addSelectionHandler(
				new SelectionHandler<Integer>() {

					@Override
					public void onSelection(SelectionEvent<Integer> event) {
						int selectIndex = event.getSelectedItem();
						switch (selectIndex) {
						case 0:
							History.newItem("browse", true);
							break;
						case 1:
							History.newItem("upload", false);
							break;
						case 2:
							History.newItem("user", false);
							break;
						default:
							History.newItem("browse", false);
							break;
						}
					}
				});
		this.videosView.getUploadView().getSaveButton().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						RequestBuilder builder = new RequestBuilder(
								RequestBuilder.GET, "../upload.json");
						builder.setCallback(new RequestCallback() {

							@Override
							public void onResponseReceived(Request request,
									Response response) {
								if (response.getStatusCode() == 403) {
									handlerManager
											.fireEvent(new AccessDeniedEvent());
									return;
								}
							}

							@Override
							public void onError(Request request, Throwable e) {
								Window.alert("error!");
							}
						});
						
						try {
							builder.send();
						} catch (RequestException e) {
							e.printStackTrace();
						}
					}
				});
	}

	@Override
	public void go(HasWidgets container) {
		if (History.getToken().equals("")) {
			History.newItem("browse");
		} else {
			History.fireCurrentHistoryState();
		}
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();

		if (token != null) {

			if (token.equals("upload")) {
				this.videosView.getTabPanel().selectTab(1);
			} else if (token.equals("user")) {
				this.videosView.getTabPanel().selectTab(2);
			} else if (token.equals("browse")) {
				this.videosView.getTabPanel().selectTab(0);
				getBrowseData();
			}
		}
		container.add(videosView);
	}

	private void getBrowseData() {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET,
				"../browse.json");

		builder.setCallback(new RequestCallback() {

			@Override
			public void onResponseReceived(Request request, Response response) {
				if (response.getStatusCode() == 401) {
					handlerManager.fireEvent(new NeedLoginEvent());
				} else {
					videosView.getBrowseWidget().clear();
					VideoItem videoItem = VideoItem
							.fromJson("{'id':'1','title':'阿凡达','content':'阿凡达（Avatar）是一部科幻电影，由著名导演詹姆斯·卡梅隆执导，二十世纪福克斯出品。该影片预算超过5亿美元，成为电影史上预算最高的电影。此外，由卡梅隆导演注入心血的全平台同名游戏《阿凡达（James Camerons Avatar: The Game）》已于2009年12月1日率先推出，游戏类型为TPS（第三人科幻称射击动作游戏），支持3D显示器。该片有3D、平面胶片、IMAX胶片三种制式供观众选择。'}");
					videosView.getBrowseWidget().add(
							new VideoItemView(videoItem));
				}
			}

			@Override
			public void onError(Request request, Throwable e) {
				Window.alert("error");
			}
		});

		try {
			builder.send();
		} catch (RequestException e1) {
			e1.printStackTrace();
		}

	}

}
