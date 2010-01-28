package com.easymorse.videos.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AuthenticationSuccessEvent extends
		GwtEvent<AuthenticationSuccessEventHandler> {

	public static Type<AuthenticationSuccessEventHandler> TYPE = new Type<AuthenticationSuccessEventHandler>();

	@Override
	protected void dispatch(
			AuthenticationSuccessEventHandler authenticationSuccessEventHandler) {
		authenticationSuccessEventHandler.onAuthenticationSuccess(this);
	}

	@Override
	public Type<AuthenticationSuccessEventHandler> getAssociatedType() {
		return TYPE;
	}

}
