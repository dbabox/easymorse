package com.easymorse.videos.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UserView extends Composite {
	private Button saveButton;

	public UserView() {
		DecoratorPanel panel = new DecoratorPanel();
		panel.setWidth("100%");
		panel.setTitle("用户认证信息");
		initWidget(panel);
		
		VerticalPanel verticalPanel=new VerticalPanel();
		panel.add(verticalPanel);
		verticalPanel.setSpacing(5);

		FlexTable table = new FlexTable();
		table.setWidth("28em");
		verticalPanel.add(table);

		table.setWidget(0, 0, new Label("用户名"));
		table.setWidget(0, 1, new TextBox());
		table.setWidget(1, 0, new Label("密码"));
		table.setWidget(1, 1, new PasswordTextBox());
		table.setWidget(2, 0, new Label("重输密码"));
		table.setWidget(2, 1, new PasswordTextBox());
		
		HorizontalPanel horizontalPanel=new HorizontalPanel();
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setSpacing(5);
		saveButton=new Button("保存");
		horizontalPanel.add(saveButton);
		
	}
	
	public Button getSaveButton() {
		return saveButton;
	}
}
