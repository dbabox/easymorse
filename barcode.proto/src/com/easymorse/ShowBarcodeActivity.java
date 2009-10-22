package com.easymorse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ShowBarcodeActivity extends Activity {

	private Button button;

	private TextView textView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		this.button = (Button) this.findViewById(R.id.Button01);
		this.button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(
						"com.google.zxing.client.android.SCAN");
				startActivityForResult(intent, 0);
			}
		});

		this.textView = (TextView) this.findViewById(R.id.hello);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode != 0) {
			return;
		}

		this.textView.setText(data.getStringExtra("SCAN_RESULT"));
	}
}