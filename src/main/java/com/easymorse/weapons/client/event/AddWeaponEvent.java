package com.easymorse.weapons.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AddWeaponEvent extends GwtEvent<AddWeaponEventHandler>{
	
	public static Type<AddWeaponEventHandler> TYPE = new Type<AddWeaponEventHandler>();

	@Override
	protected void dispatch(AddWeaponEventHandler handler) {
		handler.onAddContact(this);
	}

	@Override
	public GwtEvent.Type<AddWeaponEventHandler> getAssociatedType() {
		return TYPE;
	}

}
