package com.easymorse.listview;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;

public class RiverContentProvider extends ContentProvider {

	public static final Uri CONTENT_URI = Uri
			.parse("content://com.easymorse.cp.rivers");

	public static final String _ID = "_id";

	public static final String NAME = "name";

	public static final String LENGTH = "length";

	private static SQLiteDatabase database;

	private static final int DATABASE_VERSION = 2;

	private static final List<River> RIVERS = new ArrayList<River>();

	static {
		River river = new River("长江", 6380);
		RIVERS.add(river);

		river = new River("黄河", 5464);
		RIVERS.add(river);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues contentValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		database = new RiverDatabaseHelper(getContext(), "rivers", null,
				DATABASE_VERSION).getReadableDatabase();
		return database != null;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		return database.query("rivers", projection, selection, selectionArgs,
				null, null, sortOrder);
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	private static class RiverDatabaseHelper extends SQLiteOpenHelper {

		public RiverDatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase database) {
			database.execSQL("create table if not exists rivers("
					+ " _id integer primary key autoincrement," + " name text,"
					+ "length integer" + ");");

			SQLiteStatement statement = database
					.compileStatement("insert into rivers(name,length) values(?,?)");

			for (River r : RIVERS) {
				int index = 1;
				statement.bindString(index++, r.getName());
				statement.bindLong(index++, r.getLength());
				statement.executeInsert();
			}

			statement.close();
		}

		@Override
		public void onUpgrade(SQLiteDatabase database, int oldVersion,
				int newVersion) {
			database.execSQL("drop table if exists rivers");
			onCreate(database);
		}

	}

}
