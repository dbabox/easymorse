package com.easymorse.cp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.util.Log;

public class MyContentProvider extends ContentProvider {

	public static final Uri CONTENT_URI = Uri.parse("content://com.easymorse.cp.mycp");

	public static final String _ID = "id";

	public static final String NAME = "name";

	public static final String DYNASTY = "dynasty";

	public static final String START_YEAR = "start_year";

	private static SQLiteDatabase database;

	private static void createTablesIfNotExists() {
		database.execSQL("create table if not exists emperors("
				+ " id integer primary key autoincrement," + " name text,"
				+ "dynasty text," + "start_year text" + ");");

		SQLiteStatement statement = database
				.compileStatement("insert into emperors(name,dynasty,start_year) values(?,?,?)");
		int index = 1;
		statement.bindString(index++, "朱元璋");
		statement.bindString(index++, "明");
		statement.bindString(index++, "1398");
		statement.execute();

		index = 1;
		statement.bindString(index++, "玄烨");
		statement.bindString(index++, "清");
		statement.bindString(index++, "1722");
		statement.execute();

		statement.close();
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues contentValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		if (database == null) {
			database = this.getContext().openOrCreateDatabase("emperors",
					Context.MODE_PRIVATE, null);
			createTablesIfNotExists();
		}
		return database != null;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Cursor cursor = database.rawQuery("select * from emperors", null);
		Log.i("mycp", "cursor>" + cursor);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues contentValues, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
