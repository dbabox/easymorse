package com.easymorse;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;

public class CustomerPlayer extends TabActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		final TabHost tabHost = getTabHost();
		
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("栏目内容")
				.setContent(new Intent(this, ContentList.class)));

		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("栏目列表")
				.setContent(new Intent(this, ItemList.class)));
	}
}