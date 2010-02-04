package com.easymorse.videos.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UploadView extends Composite {

	private FormPanel formPanel;

	private Button saveButton;

	private TextArea content;

	private TextBox videoTitle;

	private FileUpload file;
	
	public FileUpload getFile() {
		return file;
	}
	
	public FormPanel getFormPanel() {
		return formPanel;
	}

	public TextBox getVideoTitle() {
		return videoTitle;
	}

	public TextArea getContent() {
		return content;
	}

	public UploadView() {
		DecoratorPanel panel = new DecoratorPanel();
		panel.setTitle("上传视频");
		initWidget(panel);

		formPanel = new FormPanel();
		panel.add(formPanel);

		VerticalPanel verticalPanel = new VerticalPanel();
		formPanel.add(verticalPanel);
		verticalPanel.setSpacing(5);

		FlexTable table = new FlexTable();
		table.setWidth("28em");
		verticalPanel.add(table);

		table.setWidget(0, 0, new Label("标题"));
		videoTitle = new TextBox();
		videoTitle.setName("title");
		table.setWidget(0, 1, videoTitle);
		table.setWidget(1, 0, new Label("视频文件"));
		file = new FileUpload();
		file.setName("file");
		table.setWidget(1, 1, file);
		table.setWidget(2, 0, new Label("介绍"));
		content = new TextArea();
		content.setName("content");
		content.setVisibleLines(4);
		table.setWidget(2, 1, content);

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setSpacing(5);
		saveButton = new Button("保存");
		horizontalPanel.add(saveButton);
	}

	public Button getSaveButton() {
		return saveButton;
	}
}
