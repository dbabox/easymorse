package com.easymorse;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;

public class CustomerPlayer extends TabActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		final TabHost tabHost = getTabHost();

		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("栏目内容")
				.setContent(new TabContentFactory() {

					@Override
					public View createTabContent(String tag) {
						ListView listView = new ListView(CustomerPlayer.this);
						listView.setAdapter(new ArrayAdapter<String>(
								CustomerPlayer.this,
								android.R.layout.simple_list_item_1,
								new String[] { "c1", "c2", "c3" }));
						return listView;
					}
				}));

		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("栏目列表")
				.setContent(new Intent(this, ItemList.class)));
	}
}