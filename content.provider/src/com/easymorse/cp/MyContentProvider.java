package com.easymorse.cp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;

public class MyContentProvider extends ContentProvider {

	private static final String PROVIDER_NAME = "com.easymorse.cp.mycp";

	public static final Uri CONTENT_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/emperors");

	public static final String _ID = "id";

	public static final String NAME = "name";

	public static final String DYNASTY = "dynasty";

	public static final String START_YEAR = "start_year";

	public static final String IMAGE = "image";

	private static SQLiteDatabase database;

	private static final String TABLE_EMPERORS = "emperors";

	private static final int ITEMS = 1;

	private static final int ITEM = 2;

	private static UriMatcher uriMatcher;

	private static final int DATABASE_VERSION = 3;

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(PROVIDER_NAME, TABLE_EMPERORS, ITEMS);
		uriMatcher.addURI(PROVIDER_NAME, TABLE_EMPERORS + "/#", ITEM);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		switch (uriMatcher.match(uri)) {
		case ITEM:
			return database.delete(TABLE_EMPERORS,
					_ID + "=" + uri.getPathSegments().get(1) + " and ("
							+ selection + ")", selectionArgs);
		case ITEMS:
			return database.delete(TABLE_EMPERORS, selection, selectionArgs);
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	@Override
	public ParcelFileDescriptor openFile(Uri uri, String mode)
			throws FileNotFoundException {
		return ParcelFileDescriptor.open(new File(
				"/data/data/com.easymorse.cp/files", uri.getPathSegments().get(
						1)), ParcelFileDescriptor.MODE_READ_ONLY);
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case ITEMS:
			return "vnd.android.cursor.dir/vnd.easymorse.mycp";
		case ITEM:
			return "vnd.android.cursor.item/vnd.easymorse.mycp";
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues contentValues) {
		long rowId = database.insert(TABLE_EMPERORS, "", contentValues);

		if (!(rowId > 0)) {
			throw new SQLException("failed to insert row into " + uri);
		}

		Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowId);
		getContext().getContentResolver().notifyChange(_uri, null);
		return _uri;
	}

	@Override
	public boolean onCreate() {
		database = new MyDatabaseHelper(getContext(), TABLE_EMPERORS, null,
				DATABASE_VERSION).getWritableDatabase();
		return database != null;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		switch (uriMatcher.match(uri)) {
		case ITEMS:
			return database.query(TABLE_EMPERORS, projection, selection,
					selectionArgs, null, null, sortOrder);
		case ITEM:
			return database.query(TABLE_EMPERORS, projection, _ID + "="
					+ uri.getPathSegments().get(1), selectionArgs, null, null,
					null);
		default:
			throw new IllegalArgumentException("unknown uri: " + uri);
		}
	}

	@Override
	public int update(Uri uri, ContentValues contentValues, String selection,
			String[] selectionArgs) {
		switch (uriMatcher.match(uri)) {
		case ITEM:
			return database.update(TABLE_EMPERORS, contentValues,
					_ID + "=" + uri.getPathSegments().get(1) + " and ("
							+ selection + ")", selectionArgs);
		case ITEMS:
			return database.update(TABLE_EMPERORS, contentValues, selection,
					selectionArgs);
		default:
			throw new IllegalArgumentException("unknown uri: " + uri);
		}
	}

	private static class MyDatabaseHelper extends SQLiteOpenHelper {

		private Context context;

		public MyDatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
			this.context = context;
		}

		private String getImageData(int rawId) {
			String uri = "";
			InputStream inputStream = context.getResources().openRawResource(
					rawId);

			byte[] data = new byte[1024 * 100];

			try {
				FileOutputStream outputStream = context.openFileOutput(rawId
						+ ".png", Activity.MODE_WORLD_WRITEABLE);
				for (int i = inputStream.read(data); i > 0; i = inputStream
						.read(data)) {
					outputStream.write(data, 0, i);
				}

				inputStream.close();
				outputStream.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			return rawId + ".png";
		}

		@Override
		public void onCreate(SQLiteDatabase database) {
			database.execSQL("create table if not exists emperors("
					+ " id integer primary key autoincrement," + " name text,"
					+ "dynasty text," + "start_year text," + "image text"
					+ ");");

			SQLiteStatement statement = database
					.compileStatement("insert into emperors(name,dynasty,start_year,image) values(?,?,?,?)");
			int index = 1;
			statement.bindString(index++, "朱元璋");
			statement.bindString(index++, "明");
			statement.bindString(index++, "1398");
			statement.bindString(index++, getImageData(R.raw.e1));
			statement.execute();

			index = 1;
			statement.bindString(index++, "玄烨");
			statement.bindString(index++, "清");
			statement.bindString(index++, "1722");
			statement.bindString(index++, getImageData(R.raw.e2));
			statement.execute();

			statement.close();
		}

		@Override
		public void onUpgrade(SQLiteDatabase database, int oldVersion,
				int newVersion) {
			Log.w("mycp", "updating database from version " + oldVersion
					+ " to " + newVersion);
			database.execSQL("drop table if exists emperors");
			onCreate(database);
		}

	}

}
