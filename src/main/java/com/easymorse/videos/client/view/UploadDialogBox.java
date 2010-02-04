package com.easymorse.videos.client.view;

import com.easymorse.videos.client.event.BrowseVideoItemsEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;

public class UploadDialogBox extends DialogBox implements SubmitHandler,
		SubmitCompleteHandler {

	FormPanel formPanel;
	private HandlerManager handlerManager;

	public UploadDialogBox(FormPanel formPanel,HandlerManager handlerManager) {
		this.formPanel = formPanel;
		this.handlerManager=handlerManager;
		this.setText("操作提示");
		this.setAnimationEnabled(true);
		this.setGlassEnabled(true);
		bind();
	}

	private void bind() {
		formPanel.addSubmitHandler(this);
		formPanel.addSubmitCompleteHandler(this);
	}

	@Override
	public void onSubmitComplete(SubmitCompleteEvent event) {
		this.clear();
		VerticalPanel panel = new VerticalPanel();
		this.add(panel);
		int width=200;
		panel.setWidth(width+"px");
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

	protected void chooseImage() {
		this.clear();
		HorizontalPanel panel = new HorizontalPanel();
		panel.setSpacing(5);
		this.add(panel);
		int width=300;
		panel.setWidth(width+"px");
		this.setPosition(width);
		
		panel.add(new Image("1.jpg"));
		
		VerticalPanel buttonPanel=new VerticalPanel();
		panel.add(buttonPanel);
		buttonPanel.setSpacing(5);
		buttonPanel.add(new Button("上一帧"));
		buttonPanel.add(new Button("下一帧"));
		
		Button confirmButton=new Button("确　定");
		confirmButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
				formPanel.reset();
				new UploadDialogBox(formPanel, handlerManager);
				handlerManager.fireEvent(new BrowseVideoItemsEvent(1));
			}
		});
		buttonPanel.add(confirmButton);
	}

	@Override
	public void onSubmit(final SubmitEvent event) {
		VerticalPanel panel = new VerticalPanel();
		this.add(panel);
		int width=200;
		panel.setWidth(width+"px");
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
				UploadDialogBox.this.hide();
			}
		});
		panel.add(button);

		this.setPosition(width);
		this.show();
	}

	protected void setPosition(int width) {
		int left = (formPanel.getOffsetWidth() - width) / 2
				+ formPanel.getAbsoluteLeft();
		int top = (formPanel.getOffsetHeight() - width/2) / 2
				+ formPanel.getAbsoluteTop();

		this.setPopupPosition(left, top);

	}

}
