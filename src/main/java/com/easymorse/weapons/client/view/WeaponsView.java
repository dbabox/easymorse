package com.easymorse.weapons.client.view;

import java.util.ArrayList;
import java.util.List;

import com.easymorse.weapons.client.model.Weapon;
import com.easymorse.weapons.client.presenter.WeaponsPresenter;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class WeaponsView extends Composite implements WeaponsPresenter.Display {
	private FlexTable contentTable;
	private FlexTable contactsTable;
	private Button addButton;
	private Button deleteButton;

	public WeaponsView() {
		DecoratorPanel contentTableDecorator = new DecoratorPanel();
		initWidget(contentTableDecorator);
		contentTableDecorator.setWidth("100%");
		contentTableDecorator.setWidth("15em");

		contentTable = new FlexTable();
		contentTable.setWidth("100%");
		contentTable.getCellFormatter().addStyleName(0, 0,
				"contacts-ListContainer");
		contentTable.getCellFormatter().setWidth(0, 0, "100%");
		contentTable.getFlexCellFormatter().setVerticalAlignment(0, 0,
				DockPanel.ALIGN_TOP);

		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.setBorderWidth(0);
		hPanel.setSpacing(0);
		hPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
		addButton = new Button("增加");
		hPanel.add(addButton);
		deleteButton = new Button("删除");
		hPanel.add(deleteButton);
		contentTable.getCellFormatter().addStyleName(0, 0, "contacts-ListMenu");
		contentTable.setWidget(0, 0, hPanel);

		contactsTable = new FlexTable();
		contactsTable.setCellSpacing(0);
		contactsTable.setCellPadding(0);
		contactsTable.setWidth("100%");
		contactsTable.addStyleName("contacts-ListContents");
		contactsTable.getColumnFormatter().setWidth(0, "15px");
		contentTable.setWidget(1, 0, contactsTable);

		contentTableDecorator.add(contentTable);
	}

	@Override
	public void setData(JsArray<Weapon> data) {
		contactsTable.removeAllRows();

		for (int i = 0; i < data.length(); ++i) {
			contactsTable.setWidget(i, 0, new CheckBox());
			contactsTable.setText(i, 1, data.get(i).getName());
		}
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public HasClickHandlers getDeleteButton() {
		return this.deleteButton;
	}

	@Override
	public List<Integer> getSelectedRows() {
		List<Integer> selectedRows = new ArrayList<Integer>();

		for (int i = 0; i < contactsTable.getRowCount(); ++i) {
			CheckBox checkBox = (CheckBox) contactsTable.getWidget(i, 0);
			if (checkBox.getValue()) {
				selectedRows.add(i);
			}
		}

		return selectedRows;
	}
}
