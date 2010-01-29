package com.easymorse.videos.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UploadView extends Composite {
	public UploadView() {
		DecoratorPanel panel = new DecoratorPanel();
		panel.setTitle("上传视频");
		initWidget(panel);

		VerticalPanel verticalPanel = new VerticalPanel();
		panel.add(verticalPanel);
		verticalPanel.setSpacing(5);

		FlexTable table = new FlexTable();
		table.setWidth("28em");
		verticalPanel.add(table);

		table.setWidget(0, 0, new Label("标题"));
		table.setWidget(0, 1, new TextBox());
		table.setWidget(1, 0, new Label("视频文件"));
		table.setWidget(1, 1, new FileUpload());
		table.setWidget(2, 0, new Label("介绍"));
		TextArea area=new TextArea();
		area.setVisibleLines(4);
		table.setWidget(2, 1, area);

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setSpacing(5);
		Button saveButton = new Button("保存");
		horizontalPanel.add(saveButton);
	}
}
