package com.easymorse;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class ContentList extends ListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, new String[] { "c1", "c2",
						"c3" }));
	}
}
