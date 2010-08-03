package com.easymorse.cp;

import android.app.Activity;
import android.content.ContentValues;
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
		// modifyRecord();
		this.setContentView(textView);
	}

	private void modifyRecord() {
		ContentValues values = new ContentValues();
		values.put(MyContentProvider.NAME, "朱重八");
		getContentResolver().update(MyContentProvider.CONTENT_URI, values,
				MyContentProvider.NAME + "=?", new String[] { "朱元璋" });
	}

	private String getContentProviderValues() {
		StringBuilder builder = new StringBuilder();

		// 查名称和朝代，朝代=明，而且按照登基时间倒排序
		Cursor cursor = managedQuery(MyContentProvider.CONTENT_URI,
				new String[] { MyContentProvider.NAME,
						MyContentProvider.DYNASTY }, MyContentProvider.DYNASTY
						+ "=?", new String[] { "明" }, " start_year desc");

		// 查全部记录
		// Cursor cursor = managedQuery(MyContentProvider.CONTENT_URI, null,
		// null,
		// null, null);

		// 根据id定位记录（0..1）
		// Cursor cursor = managedQuery(ContentUris.withAppendedId(
		// MyContentProvider.CONTENT_URI, 1),
		// new String[] { MyContentProvider.NAME }, null, null, null);

		while (cursor.moveToNext()) {
			builder
					.append(
							cursor.getString(cursor
									.getColumnIndex(MyContentProvider.NAME)))
					.append(" | ")
					// .append(
					// cursor
					// .getString(cursor
					// .getColumnIndex(MyContentProvider.START_YEAR)))
					// .append(" | ")
					.append(
							cursor.getString(cursor
									.getColumnIndex(MyContentProvider.DYNASTY)))
					.append("\n");
		}

		return builder.toString();
	}
}