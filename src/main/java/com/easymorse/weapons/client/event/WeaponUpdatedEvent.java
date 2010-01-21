package com.easymorse.weapons.client.event;

import com.easymorse.weapons.client.model.Weapon;
import com.google.gwt.event.shared.GwtEvent;

public class WeaponUpdatedEvent extends GwtEvent<WeaponUpdatedEventHandler> {

	public static Type<WeaponUpdatedEventHandler> TYPE = new Type<WeaponUpdatedEventHandler>();

	private final Weapon updatedWeapon;

	public WeaponUpdatedEvent(Weapon updatedWeapon) {
		this.updatedWeapon = updatedWeapon;
	}

	public Weapon getUpdatedWeapon() {
		return updatedWeapon;
	}

	@Override
	protected void dispatch(WeaponUpdatedEventHandler handler) {
		handler.onContactUpdated(this);
	}

	@Override
	public GwtEvent.Type<WeaponUpdatedEventHandler> getAssociatedType() {
		return TYPE;
	}

}
