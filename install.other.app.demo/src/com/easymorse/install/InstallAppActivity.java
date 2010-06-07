package com.easymorse.install;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class InstallAppActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button button = (Button) this.findViewById(R.id.clickButton);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				IntentIntegrator.initiateScan(InstallAppActivity.this);
			}
		});
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, intent);
		if (scanResult != null) {
			TextView textView=(TextView) this.findViewById(R.id.text);
			textView.setText(scanResult.getContents());
		}
	}
}