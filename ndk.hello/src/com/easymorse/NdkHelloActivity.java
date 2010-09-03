package com.easymorse;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class NdkHelloActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView textView = new TextView(this);
		setContentView(textView);

		textView.setText(sayHello());
	}

	public native String sayHello();

	static {
		System.loadLibrary("helloworld-jni");
	}
}