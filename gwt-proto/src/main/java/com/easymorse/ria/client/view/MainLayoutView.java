package com.easymorse.ria.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class MainLayoutView extends Composite {

	private static MainLayoutViewUiBinder uiBinder = GWT
			.create(MainLayoutViewUiBinder.class);

	interface MainLayoutViewUiBinder extends UiBinder<Widget, MainLayoutView> {
	}

	@UiField
	Grid content;
	
	
	public MainLayoutView(String text) {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
