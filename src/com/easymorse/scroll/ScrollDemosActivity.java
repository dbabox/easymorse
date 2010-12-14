package com.easymorse.scroll;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class ScrollDemosActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		View view = this.findViewById(R.id.myView);
		view.scrollBy(-100, 0);
	}
}