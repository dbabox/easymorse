package com.easymorse.weapons.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class EditWeaponEvent extends GwtEvent<EditWeaponEventHandler> {

	public static Type<EditWeaponEventHandler> TYPE = new Type<EditWeaponEventHandler>();

	private String id;

	public EditWeaponEvent(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	protected void dispatch(EditWeaponEventHandler handler) {
		handler.onEditWeapon(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<EditWeaponEventHandler> getAssociatedType() {
		return TYPE;
	}

}
