package com.easymorse.videos.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.LazyPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class VideosView extends Composite {

	private Button logoffButton;

	private DialogBox logoffDialogBox;

	private DecoratedTabPanel tabPanel;

	private VerticalPanel browsePanel;

	public DialogBox getLogoffDialogBox() {
		return logoffDialogBox;
	}

	public Button getLogoffButton() {
		return logoffButton;
	}

	public VideosView() {
		VerticalPanel panel = new VerticalPanel();
		panel.setWidth("500px");
		panel.setSpacing(5);
		initWidget(panel);

		HorizontalPanel titlePanel = new HorizontalPanel();
		titlePanel.setWidth("100%");
		titlePanel.setSpacing(5);
		titlePanel.add(new HTML("<h2>Marshal的视频列表</h2>"));
		titlePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

		Anchor anchor = new Anchor("退出");
		logoffButton = new Button("退出");
		anchor.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				logoffDialogBox = new DialogBox();
				logoffDialogBox.setText("操作提示");
				logoffDialogBox.setAnimationEnabled(true);
				logoffDialogBox.setGlassEnabled(true);

				VerticalPanel panel = new VerticalPanel();
				panel.setWidth("12em");
				panel.setHeight("3em");
				panel.add(new HTML("是否真的退出？"));
				panel.add(new HTML());

				Button cancelButton = new Button("取消");
				cancelButton.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						logoffDialogBox.hide();
					}
				});

				HorizontalPanel buttonPanel = new HorizontalPanel();
				buttonPanel
						.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
				buttonPanel.add(logoffButton);
				buttonPanel.add(cancelButton);

				panel.add(buttonPanel);
				logoffDialogBox.add(panel);

				logoffDialogBox.center();
				logoffDialogBox.show();
			}
		});
		titlePanel.add(anchor);
		panel.add(titlePanel);

		tabPanel = new DecoratedTabPanel();

		tabPanel.setWidth("100%");
		tabPanel.setAnimationEnabled(true);

		tabPanel.add(getBrowseWidget(), "浏览视频");
		tabPanel.add(new UploadView(), "上传视频");
		tabPanel.add(new LazyPanel() {
			@Override
			protected Widget createWidget() {
				return new UserView();
			}
		}, "用户信息");

		tabPanel.selectTab(0);

		panel.add(tabPanel);
	}

	public DecoratedTabPanel getTabPanel() {
		return tabPanel;
	}

	public  Panel getBrowseWidget() {
		if (this.browsePanel == null) {
			VerticalPanel panel = new VerticalPanel();
			panel.setWidth("100%");
			this.browsePanel=panel;
		}
		return browsePanel;
	}

}
