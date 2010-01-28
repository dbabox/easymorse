package com.easymorse.videos.client.presenter;

import com.easymorse.videos.client.event.AuthenticationSuccessEvent;
import com.easymorse.videos.client.view.LoginDialogBox;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;

public class LoginPresenter implements Presenter {

	private HandlerManager handlerManager;

	private LoginDialogBox loginDialogBox;

	public LoginPresenter(HandlerManager handlerManager) {
		this.handlerManager = handlerManager;
		loginDialogBox = this.getLoginDialogBox();
	}

	@Override
	public void go(HasWidgets container) {

		loginDialogBox.getLoginButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				login(loginDialogBox);
			}
		});
	}

	protected void login(LoginDialogBox loginDialogBox) {
		// if (loginDialogBox.getUserName().getValue().equals("zhang3")) {
		loginDialogBox.hide();
		this.handlerManager.fireEvent(new AuthenticationSuccessEvent());
		// } else {
		// loginDialogBox.setMessage("用户名或者密码错误");
		// }
	}

	private LoginDialogBox getLoginDialogBox() {
		LoginDialogBox loginView = new LoginDialogBox();
		loginView.setAnimationEnabled(true);
		loginView.setGlassEnabled(true);
		loginView.center();
		loginView.show();

		return loginView;
	}
}
