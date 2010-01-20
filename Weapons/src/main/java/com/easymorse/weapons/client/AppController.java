package com.easymorse.weapons.client;

import com.easymorse.weapons.client.event.AddWeaponEvent;
import com.easymorse.weapons.client.event.AddWeaponEventHandler;
import com.easymorse.weapons.client.event.EditWeaponCancelledEvent;
import com.easymorse.weapons.client.event.EditWeaponCancelledEventHandler;
import com.easymorse.weapons.client.event.WeaponUpdatedEvent;
import com.easymorse.weapons.client.event.WeaponUpdatedEventHandler;
import com.easymorse.weapons.client.presenter.EditWeaponPresenter;
import com.easymorse.weapons.client.presenter.Presenter;
import com.easymorse.weapons.client.presenter.WeaponsPresenter;
import com.easymorse.weapons.client.view.EditWeaponView;
import com.easymorse.weapons.client.view.WeaponsView;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;

public class AppController implements Presenter, ValueChangeHandler<String> {

	private final HandlerManager eventBus;
	private HasWidgets container;

	public AppController(HandlerManager eventBus) {
		this.eventBus = eventBus;
		this.bind();
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();

		if (token != null) {
			Presenter presenter = null;

			if (token.equals("list")) {
				presenter = new WeaponsPresenter(eventBus, new WeaponsView());
			}

			if (token.equals("add")) {
				presenter = new EditWeaponPresenter(eventBus,
						new EditWeaponView());
			}

			if (presenter != null) {
				presenter.go(container);
			}
		}
	}

	private void bind() {
		History.addValueChangeHandler(this);

		this.eventBus.addHandler(AddWeaponEvent.TYPE,
				new AddWeaponEventHandler() {
					public void onAddContact(AddWeaponEvent event) {
						doAddNewWeapon();
					}
				});
		this.eventBus.addHandler(EditWeaponCancelledEvent.TYPE,
				new EditWeaponCancelledEventHandler() {
					@Override
					public void onEditWeaponCancelled(
							EditWeaponCancelledEvent event) {
						doEditWeaponCancelled();
					}
				});
		eventBus.addHandler(WeaponUpdatedEvent.TYPE,
				new WeaponUpdatedEventHandler() {
					public void onContactUpdated(WeaponUpdatedEvent event) {
						doContactUpdated();
					}
				});
	}

	protected void doContactUpdated() {
		History.newItem("list");
	}

	protected void doEditWeaponCancelled() {
		History.newItem("list");
	}

	protected void doAddNewWeapon() {
		History.newItem("add");
	}

	@Override
	public void go(HasWidgets container) {
		this.container = container;

		if ("".equals(History.getToken())) {
			History.newItem("list");
		} else {
			History.fireCurrentHistoryState();
		}
	}

}
