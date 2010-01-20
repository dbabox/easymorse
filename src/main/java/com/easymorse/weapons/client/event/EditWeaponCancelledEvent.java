package com.easymorse.weapons.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class EditWeaponCancelledEvent extends GwtEvent<EditWeaponCancelledEventHandler> {

	public static Type<EditWeaponCancelledEventHandler> TYPE = new Type<EditWeaponCancelledEventHandler>();

	@Override
	protected void dispatch(EditWeaponCancelledEventHandler handler) {
		handler.onEditWeaponCancelled(this);
	}

	@Override
	public GwtEvent.Type<EditWeaponCancelledEventHandler> getAssociatedType() {
		return TYPE;
	}

}
