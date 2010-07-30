package com.easymorse.cp;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class UseContactActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TextView textView = new TextView(this);
		textView.setText(getContentProviderValues());
		this.setContentView(textView);
	}

	private String getContentProviderValues() {
		StringBuilder builder = new StringBuilder();

		Cursor cursor = managedQuery(MyContentProvider.CONTENT_URI, null, null,
				null, null);
		while (cursor.moveToNext()) {
			builder
					.append(
							cursor.getString(cursor
									.getColumnIndex(MyContentProvider.NAME)))
					.append(" | ")
					.append(
							cursor
									.getString(cursor
											.getColumnIndex(MyContentProvider.START_YEAR)))
					.append(" | ")
					.append(
							cursor.getString(cursor
									.getColumnIndex(MyContentProvider.DYNASTY)))
					.append("\n");
		}

		return builder.toString();
	}
}