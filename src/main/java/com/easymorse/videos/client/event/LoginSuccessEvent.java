package com.easymorse.videos.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class LoginSuccessEvent extends GwtEvent<LoginSuccessEventHandler> {

	public static Type<LoginSuccessEventHandler> TYPE = new Type<LoginSuccessEventHandler>();
	private String userName;

	public LoginSuccessEvent(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	@Override
	protected void dispatch(
			LoginSuccessEventHandler authenticationSuccessEventHandler) {
		authenticationSuccessEventHandler.onAuthenticationSuccess(this);
	}

	@Override
	public Type<LoginSuccessEventHandler> getAssociatedType() {
		return TYPE;
	}

}
