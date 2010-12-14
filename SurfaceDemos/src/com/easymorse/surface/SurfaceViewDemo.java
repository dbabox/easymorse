package com.easymorse.surface;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class SurfaceViewDemo extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = new MySurfaceView(this);
		this.setContentView(view);
		
		
		view.scrollTo(400, 100);
	}
}