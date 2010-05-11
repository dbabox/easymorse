package com.easymorse.ria.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author marshal
 *
 */
public class HelloworldView extends Composite {

	private static HelloworldViewUiBinder uiBinder = GWT
			.create(HelloworldViewUiBinder.class);

	interface HelloworldViewUiBinder extends UiBinder<Widget, HelloworldView> {
	}

	@UiField
	Button myButton;

	public HelloworldView(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		myButton.setText(firstName);
	}

	@UiHandler("myButton")
	void onClick(ClickEvent e) {
		Window.alert("Hello!");
	}

}
