package com.easymorse.weapons.client.presenter;

import com.easymorse.weapons.client.event.EditWeaponCancelledEvent;
import com.easymorse.weapons.client.event.WeaponUpdatedEvent;
import com.easymorse.weapons.client.model.Weapon;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class EditWeaponPresenter implements Presenter {

	public interface Display {
		HasClickHandlers getSaveButton();

		HasClickHandlers getCancelButton();

		HasValue<String> getName();

		HasValue<String> getDescription();

		Widget asWidget();
	}

	private HandlerManager eventBus;

	private Display display;

	private Weapon weapon;

	public EditWeaponPresenter(HandlerManager eventBus, Display display) {
		this.eventBus = eventBus;
		this.display = display;
		this.weapon = JavaScriptObject.createObject().cast();
		bind();
	}

	private void bind() {

		this.display.getSaveButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doSave();
			}
		});

		this.display.getCancelButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new EditWeaponCancelledEvent());
			}
		});
	}

	protected void doSave() {
		this.weapon.setName(this.display.getName().getValue());
		this.weapon.setDescription(this.display.getDescription().getValue());

		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.POST,
				"../save.json");
		requestBuilder.setHeader("Content-Type",
				"application/x-www-form-urlencoded");

		requestBuilder.setRequestData(new StringBuilder().append("name=")
				.append(weapon.getName()).append("&description=").append(
						weapon.getDescription()).toString());

		requestBuilder.setCallback(new RequestCallback() {

			@Override
			public void onResponseReceived(Request request, Response response) {
				eventBus.fireEvent(new WeaponUpdatedEvent(weapon));
			}

			@Override
			public void onError(Request request, Throwable e) {
				Window.alert("error");
			}
		});
		try {
			requestBuilder.send();
		} catch (RequestException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

}
