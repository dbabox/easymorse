package com.easymorse.listview.customer;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;

public class CustomerListView extends ListActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		CustomerListAdapter adapter = new CustomerListAdapter(this);

		adapter.addSectionHeaderItem("2002-3-1");

		ArrayList<ListElement> elements = new ArrayList<ListElement>();
		for (int i = 0; i < 5; i++) {
			ContentListElement element = new ContentListElement();
			element.setTitle("哈利波特第" + (i + 1) + "集");
			elements.add(element);
		}
		adapter.addList(elements);

		adapter.addSectionHeaderItem("2002-2-2");

		elements = new ArrayList<ListElement>();
		for (int i = 0; i < 3; i++) {
			ContentListElement element = new ContentListElement();
			element.setTitle("指环王第" + (i + 1) + "集");
			elements.add(element);
		}
		adapter.addList(elements);

		this.setListAdapter(adapter);
	}
}