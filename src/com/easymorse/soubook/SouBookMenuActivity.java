package com.easymorse.soubook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SouBookMenuActivity extends Activity {

	private Button searchButton;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		this.searchButton = (Button) this.findViewById(R.id.searchButton);
		this.searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(
						"com.google.zxing.client.android.SCAN");
				startActivityForResult(intent, 0);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode != 0) {
			return;
		}
		Intent intent = new Intent();
		intent.setClass(this, SearchBookActivity.class);
		intent.putExtra("ISBN", data.getExtras().getString("SCAN_RESULT"));
		this.startActivity(intent);
	}

}