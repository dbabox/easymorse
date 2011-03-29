package com.easymorse.listview;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.widget.ListView;

public class ListViewActivity extends FragmentActivity {

	private ListView riverListView;

	private SimpleCursorAdapter adapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		initLoader();
		setRiverListViewAdapter();
	}

	private void initLoader() {
		getSupportLoaderManager().initLoader(0, null,
				new LoaderCallbacks<Cursor>() {

					@Override
					public Loader<Cursor> onCreateLoader(int id, Bundle args) {
						Log.d("list", "on create loader");
						 CursorLoader cursorLoader=new CursorLoader(ListViewActivity.this,
								RiverContentProvider.CONTENT_URI, new String[] {
										RiverContentProvider._ID,
										RiverContentProvider.NAME,
										RiverContentProvider.INTRODUCTION },
								null, null, null);
						 //cursorLoader.setUpdateThrottle(1000);
						 return cursorLoader;
					}

					@Override
					public void onLoadFinished(Loader<Cursor> loader,
							Cursor cursor) {
						Log.d("list", "on loader finished");
						adapter.swapCursor(cursor);
					}

					@Override
					public void onLoaderReset(Loader<Cursor> loader) {
						Log.d("list", "on loader reset");
						adapter.swapCursor(null);
					}
				});
	}

	private void setRiverListViewAdapter() {
		riverListView = (ListView) this.findViewById(R.id.riverList);

		Cursor cursor = managedQuery(RiverContentProvider.CONTENT_URI, null,
				null, null, null);
		adapter = new SimpleCursorAdapter(this, R.layout.row, cursor,
				new String[] { RiverContentProvider.NAME,
						RiverContentProvider.INTRODUCTION }, new int[] {
						R.id.riverName, R.id.riverIntroduction }, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		riverListView.setAdapter(adapter);
	}
}