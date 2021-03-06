package com.easymorse.weapons.client.presenter;

import java.util.List;

import com.easymorse.weapons.client.event.AddWeaponEvent;
import com.easymorse.weapons.client.event.EditWeaponEvent;
import com.easymorse.weapons.client.model.Weapon;
import com.google.gwt.core.client.JsArray;
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
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class ListWeaponPresenter implements Presenter {

	public interface Display {
		void setData(JsArray<Weapon> data);

		Widget asWidget();

		HasClickHandlers getDeleteButton();

		List<Integer> getSelectedRows();

		HasClickHandlers getAddButton();

		HasClickHandlers getList();

		int getClickedRow(ClickEvent event);
	}
	
	private JsArray<Weapon> list;

	private Display display;

	private HandlerManager eventBus;

	public ListWeaponPresenter(HandlerManager eventBus, Display display) {
		this.eventBus = eventBus;
		this.display = display;
	}

	public void bind() {
		display.getDeleteButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				deleteSelectedWeapons();
			}
		});

		display.getAddButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new AddWeaponEvent());
			}
		});

		display.getList().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				int selectedRow = display.getClickedRow(event);

				if (selectedRow >= 0) {
					String id=list.get(selectedRow).getId();
					eventBus.fireEvent(new EditWeaponEvent(id));
				}
			}
		});
	}

	protected void deleteSelectedWeapons() {
		List<Integer> selectedRows = display.getSelectedRows();

		if (selectedRows != null && !selectedRows.isEmpty()) {

			RequestBuilder requestBuilder = new RequestBuilder(
					RequestBuilder.POST, "../delete.json");
			requestBuilder.setHeader("Content-Type",
					"application/x-www-form-urlencoded");

			StringBuilder builder = new StringBuilder();

			for (Integer id : selectedRows) {
				builder.append("id=").append(list.get(id).getId()).append("&");
			}

			requestBuilder.setRequestData(builder.toString());

			requestBuilder.setCallback(new RequestCallback() {

				@Override
				public void onResponseReceived(Request request,
						Response response) {
					list();
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
	}

	@Override
	public void go(HasWidgets container) {
		this.bind();
		container.clear();
		container.add(this.display.asWidget());
		this.list();
	}

	private void list() {
		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET,
				"../list.json");
		requestBuilder.setCallback(new RequestCallback() {

			@Override
			public void onError(Request request, Throwable e) {
				Window.alert("error!");
			}

			@Override
			public void onResponseReceived(Request request, Response response) {
				list=Weapon.arrayFromJson(response.getText());
				display.setData(list);
			}

		});

		try {
			requestBuilder.send();
		} catch (RequestException e) {
			e.printStackTrace();
		}
	}

}
