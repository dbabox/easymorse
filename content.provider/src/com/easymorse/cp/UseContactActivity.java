package com.easymorse.cp;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TextView;

public class UseContactActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Cursor cursor = getContentResolver().query(
		// ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
		// null, null);
		//
		// StringBuilder builder = new StringBuilder(
		// ContactsContract.CommonDataKinds.Phone.CONTENT_URI + "\n 姓名： ");
		//
		// while (cursor.moveToNext()) {
		// builder
		// .append(
		// cursor
		// .getString(cursor
		// .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)))
		// .append("-");
		// }

		TextView textView = new TextView(this);
		textView.setText(getContentProviderValues());
		this.setContentView(textView);
	}

	private String getContentProviderValues() {
		StringBuilder builder = new StringBuilder();

		Cursor cursor = managedQuery(MyContentProvider.CONTENT_URI, null, null,
				null, null);
		Log.i("mycp", "cursor>>>>>>" + cursor);
		while (cursor.moveToNext()) {
			builder.append(
					cursor.getString(cursor
							.getColumnIndex(MyContentProvider.NAME))).append(
					" | ").append(
					cursor.getColumnIndex(MyContentProvider.DYNASTY)).append(
					" | ").append(
					cursor.getColumnIndex(MyContentProvider.DYNASTY));
		}

		return builder.toString();
	}
}