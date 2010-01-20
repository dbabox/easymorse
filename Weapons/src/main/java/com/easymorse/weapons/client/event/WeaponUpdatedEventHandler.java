package com.easymorse.weapons.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface WeaponUpdatedEventHandler extends EventHandler {
	void onContactUpdated(WeaponUpdatedEvent event);
}
