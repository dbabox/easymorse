package com.easymorse.cp;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UseContactActivity extends Activity {

	ImageView imageView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		this.setContentView(layout);
		TextView textView = new TextView(this);
		textView.setText(getContentProviderValues());
		layout.addView(textView);
		layout.addView(imageView);

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
						MyContentProvider.DYNASTY, MyContentProvider.IMAGE },
				MyContentProvider.DYNASTY + "=?", new String[] { "明" },
				" start_year desc");

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

			this.imageView = new ImageView(this);
			try {
				this.imageView
						.setImageDrawable(Drawable
								.createFromStream(
										getContentResolver()
												.openInputStream(
														Uri
																.withAppendedPath(
																		MyContentProvider.CONTENT_URI,
																		cursor
																				.getString(cursor
																						.getColumnIndex(MyContentProvider.IMAGE)))),
										"a.png"));
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		}

		return builder.toString();
	}
}