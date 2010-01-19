package com.easymorse.weapons.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class WeapenDeleteEvent extends GwtEvent<WeapenDeleteEventHandler> {

	public static Type<WeapenDeleteEventHandler> TYPE = new Type<WeapenDeleteEventHandler>();

	@Override
	protected void dispatch(WeapenDeleteEventHandler handler) {
		handler.onDeleteWeapen(this);
	}

	@Override
	public GwtEvent.Type<WeapenDeleteEventHandler> getAssociatedType() {
		return TYPE;
	}

}
