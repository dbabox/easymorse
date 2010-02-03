package com.easymorse.videos.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupListener;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;

public class UploadDialogBox extends DialogBox implements SubmitHandler,SubmitCompleteHandler {
	
	FormPanel formPanel;
	
	public UploadDialogBox(FormPanel formPanel) {
		this.formPanel = formPanel;
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
		VerticalPanel panel=new VerticalPanel();
		this.add(panel);
		panel.setWidth("200px");
		panel.setSpacing(5);
		panel.add(new HTML("上传成功。"));
		Button button=new Button("关闭");
		button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				UploadDialogBox.this.hide();
			}
		});
		panel.add(button);
	}

	@Override
	public void onSubmit(final SubmitEvent event) {
		VerticalPanel panel=new VerticalPanel();
		this.add(panel);
		panel.setWidth("200px");
		panel.setSpacing(5);
		HorizontalPanel innerPanel=new HorizontalPanel();
		innerPanel.setSpacing(5);
		innerPanel.add(new Image("loading.gif"));
		innerPanel.add(new HTML("正在上传...."));
		panel.add(innerPanel);
		
		Button button=new Button("取消");
		button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent clickEvent) {
				event.cancel();
				UploadDialogBox.this.hide();
			}
		});
		panel.add(button);
		
		int left=(formPanel.getOffsetWidth()-200)/2+formPanel.getAbsoluteLeft();
		int top=(formPanel.getOffsetHeight()-100)/2+formPanel.getAbsoluteTop();
		
		this.setPopupPosition(left,top);
		this.show();
	}

}
