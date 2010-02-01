package com.easymorse.videos.client.presenter;

import com.easymorse.videos.client.event.LoginSuccessEvent;
import com.easymorse.videos.client.view.LoginDialogBox;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
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
				login();
			}
		});
	}

	protected void login() {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST,
				"../j_spring_security_check");
		builder.setHeader("Content-Type", "application/x-www-form-urlencoded");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("j_username=").append(
				this.loginDialogBox.getUserName().getValue()).append(
				"&j_password=").append(
				this.loginDialogBox.getPassword().getValue());
		
		if(this.loginDialogBox.getSaveCheckBox().getValue()){
			stringBuilder.append("&_spring_security_remember_me=on");
		}

		builder.setRequestData(stringBuilder.toString());
		builder.setCallback(new RequestCallback() {

			@Override
			public void onResponseReceived(Request request, Response response) {

				if (response.getStatusCode() >=400) {
					loginError("用户名或者密码错误");
					return;
				}
				
				loginSuccessful();
			}

			@Override
			public void onError(Request request, Throwable e) {
				loginError("服务器端问题，稍候再试");
			}

		});

		try {
			builder.send();
		} catch (RequestException e) {
			e.printStackTrace();
		}
	}

	private void loginSuccessful() {
		loginDialogBox.hide();
		this.handlerManager.fireEvent(new LoginSuccessEvent(loginDialogBox.getUserName().getValue()));
	}

	private void loginError(String errorMessage) {
		loginDialogBox.setMessage(errorMessage);
	}
//
//	private static native String getResultString(String jsonString) /*-{
//		return eval('(' + jsonString + ')').result;
//	}-*/;

	private LoginDialogBox getLoginDialogBox() {
		LoginDialogBox loginView = new LoginDialogBox();
		loginView.setAnimationEnabled(true);
		loginView.setGlassEnabled(true);
		loginView.center();
		loginView.show();

		return loginView;
	}
}
