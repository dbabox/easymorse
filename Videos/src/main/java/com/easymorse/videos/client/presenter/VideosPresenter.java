package com.easymorse.videos.client.presenter;

import com.easymorse.videos.client.event.LogOffEvent;
import com.easymorse.videos.client.view.VideosView;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
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
		bind();
	}

	private void bind() {
		History.addValueChangeHandler(this);
		this.videosView = new VideosView();

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
							History.newItem("browse", false);
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

			this.videosView.getTabPanel().selectTab(0);

			if (token.equals("upload")) {
				this.videosView.getTabPanel().selectTab(1);
			}

			if (token.equals("user")) {
				this.videosView.getTabPanel().selectTab(2);
			}
		}
		container.clear();
		container.add(videosView);
	}

}
