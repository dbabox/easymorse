package com.easymorse.videos.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class LoginDialogBox extends DialogBox {

	private TextBox userName;

	private PasswordTextBox password;

	private Button loginButton;

	private Label message=new Label();

	public LoginDialogBox() {
		userName = new TextBox();
		password = new PasswordTextBox();
		this.setText("登录");
		this.setWidget(getDialogWidget());
		userName.setFocus(true);
	}

	public TextBox getUserName() {
		return userName;
	}

	public PasswordTextBox getPassword() {
		return password;
	}

	public Button getLoginButton() {
		return loginButton;
	}

	public void setMessage(String message) {
		this.message.setText(message);
	}

	private Widget getDialogWidget() {
		VerticalPanel panel = new VerticalPanel();
		panel.setWidth("15em");

		if (message != null) {
			panel.add(message);
		}

		FlexTable table = new FlexTable();
		panel.add(table);

		table.setWidget(0, 0, new Label("用户名"));
		table.setWidget(0, 1, this.userName);
		table.setWidget(1, 0, new Label("密码"));
		table.setWidget(1, 1, this.password);

		HorizontalPanel buttonPanel = new HorizontalPanel();
		panel.add(buttonPanel);

		loginButton = new Button("登录");
		panel.add(loginButton);

		return panel;
	}

}
