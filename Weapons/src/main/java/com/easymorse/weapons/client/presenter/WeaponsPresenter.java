package com.easymorse.weapons.client.presenter;

import com.easymorse.weapons.client.model.Weapon;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class WeaponsPresenter implements Presenter {

	public interface Display {
		void setData(JsArray<Weapon> data);

		Widget asWidget();
	}

	private Display display;
	private HandlerManager eventBus;

	public WeaponsPresenter(HandlerManager eventBus, Display display) {
		this.eventBus = eventBus;
		this.display = display;
//		 List<String> data = Arrays.asList("T-34 坦克",
//		 "虎式坦克","零式战斗轰炸机","野马战斗机","B-25轰炸机");
//		 this.display.setData(data);
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(this.display.asWidget());

		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET,
				"../list.json");
		requestBuilder.setCallback(new RequestCallback() {

			@Override
			public void onError(Request request, Throwable e) {
				Window.alert("error!");
			}

			@Override
			public void onResponseReceived(Request request, Response response) {
				display.setData(Weapon.arrayFromJson(response.getText()));
			}

		});
		
		try {
			requestBuilder.send();
		} catch (RequestException e) {
			e.printStackTrace();
		}

	}

}
