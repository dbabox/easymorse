package com.easymorse.listview;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ListViewActivity extends Activity {

	private ListView riverListView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		setRiverListViewAdapter();
	}

	private void setRiverListViewAdapter() {
		riverListView = (ListView) this.findViewById(R.id.riverList);

		Cursor cursor = managedQuery(RiverContentProvider.CONTENT_URI, null,
				null, null, null);
		CursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.row,
				cursor, new String[] { RiverContentProvider.NAME,
						RiverContentProvider.INTRODUCTION }, new int[] {
						R.id.riverName, R.id.riverIntroduction });
		riverListView.setAdapter(adapter);
	}

	// void testCp() {
	// StringBuilder builder = new StringBuilder();
	//
	// Cursor cursor = managedQuery(RiverContentProvider.CONTENT_URI, null,
	// null, null, null);
	//
	// while (cursor.moveToNext()) {
	// builder.append(
	// cursor.getString(cursor
	// .getColumnIndex(RiverContentProvider.NAME)))
	// .append(" | ");
	// }
	//
	// Log.d("river", builder.toString());
	// }
}