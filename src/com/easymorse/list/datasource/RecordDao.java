package com.easymorse.list.datasource;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.drawable.Drawable;

import com.easymorse.list.R;

/**
 * Record dao对象
 * 
 * @author marshal
 * 
 */
public class RecordDao {

	@SuppressWarnings("unused")
	private static final String TAG = "listview";

	private static RecordDao recordDao;

	public static RecordDao getRecordDao(Context context) {
		if (recordDao == null) {
			recordDao = new RecordDao(context);
		}
		return recordDao;
	}

	// 由于模拟图片数据
	private static final int[] drawables = { R.drawable.a1, R.drawable.a2,
			R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a6,
			R.drawable.a7, R.drawable.a8, R.drawable.a9, R.drawable.a10,
			R.drawable.a12, R.drawable.a13 };

	// 图片的缓存，使用SoftReference是为了防止oom
	private static Map<Long, SoftReference<Drawable>> drawableCache = new HashMap<Long, SoftReference<Drawable>>();

	private Context context;

	private SQLiteDatabase database;

	public RecordDao(Context context) {
		this.context = context;
		init();
	}

	/**
	 * 通过缓存获取图片对象
	 * 
	 * @param id
	 *            图片的id
	 * @return
	 */
	public Drawable getDrawable(Long id) {
		// 如果缓存中有，使用缓存的
		if (drawableCache.get(id) != null
				&& drawableCache.get(id).get() != null) {
			return drawableCache.get(id).get();
		}

		// 未在缓存中，可能是没加入缓存，也可能是垃圾回收，则重新获取，并加载缓存中
		if (id >= 1 && id <= drawables.length) {
			Drawable drawable = context.getResources().getDrawable(
					drawables[id.intValue() - 1]);
			drawableCache.put(id, new SoftReference<Drawable>(drawable));
			return drawable;
		}
		return null;
	}

	private void init() {
		this.database = context.openOrCreateDatabase("mydata",
				Context.MODE_PRIVATE, null);

		// TODO 正式使用时，取消下面初始化代码，改为使用SQLiteOpenHelper
		dropTables();
		createTables();

		for (int i = 0; i < 12; i++) {
			create(new Record(null, "柴可夫司机", i));
		}
	}

	public void create(Record record) {
		SQLiteStatement statement = database
				.compileStatement("insert into mydata (name,play_times) values(?,?)");
		statement.bindString(1, record.getName());
		statement.bindLong(2, record.getPlayTimes());
		statement.execute();
		statement.close();

		// 需要设置record的id，否则没有唯一标识，无法和数据库交互
		Cursor cursor = database.rawQuery(
				"select last_insert_rowid() from mydata", new String[] {});
		if (cursor.moveToFirst()) {
			record.setId(cursor.getLong(0));
		}
		cursor.close();
	}

	public void browse(Page page) {
		StringBuilder orderBy = new StringBuilder();

		if (page.getOrderFieldName() != null) {
			orderBy.append(" order by ").append(page.getOrderFieldName());
			if (page.isOrderDesc()) {
				orderBy.append(" desc");
			}
		}

		page.setResults(new ArrayList<Record>());
		Cursor cursor = database.rawQuery("select count(*) from mydata ",
				new String[] {});
		if (cursor.moveToFirst()) {
			page.setCount(cursor.getInt(0));
		}
		cursor.close();

		if (page.getCount() > page.getSize() * (page.getNo() - 1)) {
			cursor = database.rawQuery("select id,name,play_times from mydata"
					+ orderBy + " limit " + page.getSize() * (page.getNo() - 1)
					+ " , " + page.getSize(), new String[] {});// 使用limit子句

			while (cursor.moveToNext()) {
				Record record = new Record(cursor.getLong(0),
						cursor.getString(1), cursor.getInt(2));
				page.getResults().add(record);
			}
			cursor.close();
		}
	}

	public void delete(Long id) {
		SQLiteStatement statement = database
				.compileStatement("delete from mydata where id=?");
		statement.bindLong(1, id);
		statement.execute();
		statement.close();
	}

	public Record get(Long id) {
		Record record = null;
		Cursor cursor = database.rawQuery(
				"select name,play_times from mydata where id=" + id,
				new String[] {});
		if (cursor.moveToFirst()) {
			record = new Record();
			record.setName(cursor.getString(0));
			record.setId(id);
		}
		cursor.close();
		return record;
	}

	private void dropTables() {
		database.execSQL("drop table if exists mydata");
		database.execSQL("drop table if exists tags");
	}

	private void createTables() {
		database.execSQL("create table if not exists mydata("
				+ " id integer primary key autoincrement," + " name text,"
				+ "play_times integer" + ")");
		database.execSQL("create table if not exists tags("
				+ " id integer primary key autoincrement," + " name text,"
				+ "recored_id integer" + ")");
	}
}
