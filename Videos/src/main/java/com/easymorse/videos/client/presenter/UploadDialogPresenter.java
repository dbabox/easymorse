package com.easymorse.videos.client.presenter;

import com.easymorse.videos.client.event.BrowseVideoItemsEvent;
import com.easymorse.videos.client.event.TaskCompleteEvent;
import com.easymorse.videos.client.event.TaskCompleteEventHandler;
import com.easymorse.videos.client.event.UploadCompleteEvent;
import com.easymorse.videos.client.event.UploadCompleteEventHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;

public class UploadDialogPresenter implements Presenter {

	private HandlerManager handlerManager;

	private FormPanel formPanel;

	private SubmitEvent event;

	private DialogBox box;

	private Integer second = 0;

	private String id;

	public UploadDialogPresenter(HandlerManager handlerManager,
			FormPanel formPanel, SubmitEvent event) {
		this.handlerManager = handlerManager;
		this.formPanel = formPanel;
		this.event = event;

		this.bind();
	}

	private void bind() {
		this.handlerManager.addHandler(UploadCompleteEvent.TYPE,
				new UploadCompleteEventHandler() {
					@Override
					public void onUploadComplete(UploadCompleteEvent event) {
						id = event.getId();
						box.clear();
						VerticalPanel panel = new VerticalPanel();
						box.add(panel);
						int width = 200;
						panel.setWidth(width + "px");
						panel.setSpacing(5);
						panel.add(new HTML("上传成功。"));
						Button button = new Button("下一步");
						button.addClickHandler(new ClickHandler() {
							@Override
							public void onClick(ClickEvent event) {
								chooseImage();
							}

						});
						panel.add(button);
					}
				});
	}

	protected void chooseImage() {
		box.clear();
		HorizontalPanel panel = new HorizontalPanel();
		panel.setSpacing(5);
		box.add(panel);
		int width = 300;
		panel.setWidth(width + "px");
		setPosition(width);

		final Image image = new Image("extract.json?id=" + id + "&second=" + 0
				+ "&t=" + System.currentTimeMillis());

		image.addLoadHandler(new LoadHandler() {
			@Override
			public void onLoad(LoadEvent event) {
				handlerManager.fireEvent(new TaskCompleteEvent());
			}
		});
		panel.add(image);

		VerticalPanel buttonPanel = new VerticalPanel();
		panel.add(buttonPanel);
		buttonPanel.setSpacing(5);
		Button beforeButton = new Button("上一帧");
		beforeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				second = second - 10;
				if (second < 0) {
					second = 0;
				}
				image.setUrl("extract.json?id=" + id + "&second=" + second
						+ "&t=" + System.currentTimeMillis());
				new WaitForDialogBox().show();
			}
		});
		buttonPanel.add(beforeButton);

		Button nextButton = new Button("下一帧");
		nextButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				second = second + 10;
				image.setUrl("extract.json?id=" + id + "&second=" + second
						+ "&t=" + System.currentTimeMillis());
				new WaitForDialogBox().show();
			}
		});
		buttonPanel.add(nextButton);

		Button confirmButton = new Button("确　定");
		confirmButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				box.hide();
				formPanel.reset();
				handlerManager.fireEvent(new BrowseVideoItemsEvent(1));
			}
		});
		buttonPanel.add(confirmButton);
	}

	@Override
	public void go(HasWidgets container) {
		this.createDialogBox().show();
	}

	private DialogBox createDialogBox() {
		box = new DialogBox();
		box.setText("操作提示");
		box.setAnimationEnabled(true);
		box.setGlassEnabled(true);
		setPosition(200);
		setOnSubmitView(box);
		return box;
	}

	private void setOnSubmitView(final DialogBox box) {
		VerticalPanel panel = new VerticalPanel();
		box.add(panel);
		int width = 200;
		panel.setWidth(width + "px");
		panel.setSpacing(5);
		HorizontalPanel innerPanel = new HorizontalPanel();
		innerPanel.setSpacing(5);
		innerPanel.add(new Image("loading.gif"));
		innerPanel.add(new HTML("正在上传...."));
		panel.add(innerPanel);

		Button button = new Button("取消");
		button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent clickEvent) {
				event.cancel();
				box.hide();
			}
		});
		panel.add(button);

		this.setPosition(width);
	}

	protected void setPosition(int width) {
		int left = (formPanel.getOffsetWidth() - width) / 2
				+ formPanel.getAbsoluteLeft();
		int top = (formPanel.getOffsetHeight() - width / 2) / 2
				+ formPanel.getAbsoluteTop();

		box.setPopupPosition(left, top);

	}

	class WaitForDialogBox extends DialogBox {
		public WaitForDialogBox() {
			VerticalPanel panel = new VerticalPanel();
			this.add(panel);
			int width = 150;
			panel.setWidth(width + "px");
			panel.setSpacing(5);
			HorizontalPanel innerPanel = new HorizontalPanel();
			innerPanel.setSpacing(5);
			innerPanel.add(new Image("loading.gif"));
			innerPanel.add(new HTML("图片生成中...."));
			panel.add(innerPanel);

			int left = (formPanel.getOffsetWidth() - width) / 2
					+ formPanel.getAbsoluteLeft();
			int top = (formPanel.getOffsetHeight() - width / 2) / 2
					+ formPanel.getAbsoluteTop();
			this.setPopupPosition(left, top);

			this.setText("操作提示");
			this.setAnimationEnabled(true);
			this.setGlassEnabled(true);
			
			handlerManager.addHandler(TaskCompleteEvent.TYPE, new TaskCompleteEventHandler() {
				@Override
				public void onTaskComplete(TaskCompleteEvent event) {
					hide();
				}
			});
		}
	}

}
