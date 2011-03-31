package com.easymorse.listview;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.util.Log;

/**
 * River content provider.
 * 
 * @author marshal
 * 
 */
public class RiverContentProvider extends ContentProvider {

	public static final String PROVIDER_NAME = "com.easymorse.cp.rivers";

	public static final Uri CONTENT_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/river");

	public static final String _ID = "_id";

	public static final String NAME = "name";

	public static final String LENGTH = "length";

	public static final String INTRODUCTION = "introduction";

	public static final String IMAGE_URL = "image_url";

	private static SQLiteDatabase database;

	private static final int DATABASE_VERSION = 4;

	private static final int ITEMS = 1;

	private static final int ITEM = 2;

	private static UriMatcher uriMatcher;

	static {
		/**
		 * 设置uri matcher，这里有不明白的见：http://marshal.easymorse.com/archives/2991
		 */
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(PROVIDER_NAME, "river/#", ITEM);
		uriMatcher.addURI(PROVIDER_NAME, "river/", ITEMS);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		Log.d("list", "uri match getType");
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues contentValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		Log.d("list", "on create cp");
		database = new RiverDatabaseHelper(getContext(), "rivers", null,
				DATABASE_VERSION).getWritableDatabase();

		// android.R.style.Widget_Button
		//
		// new Thread() {
		// boolean flag;
		//
		// @Override
		// public void run() {
		// try {
		// while (true) {
		// Thread.sleep(1000 * 3);
		// ContentValues values = new ContentValues();
		// if (flag) {
		// values.put(RiverContentProvider.NAME, "Long River");
		// } else {
		// values.put(RiverContentProvider.NAME, "长江");
		// }
		// flag = !flag;
		// update(RiverContentProvider.CONTENT_URI, values,
		// "_id=1", null);
		// Log.d("list", ">>>>>>>update record");
		// }
		// } catch (InterruptedException e) {
		// Log.d("list", e.getMessage());
		// }
		// }
		// }.start();

		return database != null;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Cursor cursor = null;
		switch (uriMatcher.match(uri)) {// 判断是访问river集合还是指定id访问单条记录
		case ITEMS:
			cursor = database.query("rivers", projection, selection,
					selectionArgs, null, null, sortOrder);
			cursor.setNotificationUri(getContext().getContentResolver(), uri);
			return cursor;
		case ITEM:
			cursor = database.query("rivers", projection, _ID + "="
					+ uri.getPathSegments().get(1), selectionArgs, null, null,
					sortOrder);
			cursor.setNotificationUri(getContext().getContentResolver(), uri);
			return cursor;
		default:
			throw new IllegalArgumentException("unknown uri: " + uri);
		}

	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int returnValue = database.update("rivers", values, selection,
				selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return returnValue;
	}

	private static class RiverDatabaseHelper extends SQLiteOpenHelper {

		private List<River> rivers = new ArrayList<River>();

		public RiverDatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		private void initRiverList() {
			River river = new River(
					"长江",
					6380,
					"长江（古称江、大江等）位于中国境内，发源于中国青海省唐古拉山各拉丹东雪山的姜根迪如冰川中，是中国、亚洲第一和世界第三大（同时也是长）河流，其长度仅次于尼罗河及亚马逊河，超过地球半径。",
					"http://upload.wikimedia.org/wikipedia/commons/thumb/5/58/Dusk_on_the_Yangtze_River.jpg/800px-Dusk_on_the_Yangtze_River.jpg");
			rivers.add(river);

			river = new River(
					"黄河",
					5464,
					"中国古代称河，发源于中国青海省巴颜喀拉山脉，流经青海、四川、甘肃、宁夏、内蒙古、陕西、山西、河南、山东9个省区，最后于山东省东营市垦利县注入渤海，是中国第二长河，仅次于长江，也是世界第七长河流。",
					"http://upload.wikimedia.org/wikipedia/commons/thumb/e/ea/Hukou_Waterfall.jpg/800px-Hukou_Waterfall.jpg");
			rivers.add(river);

			river = new River("辽河", 1390,
					"辽河是中华人民共和国东北地区南部的大河，流经河北、内蒙古、吉林、辽宁等省区。",
					"http://imgsrc.baidu.com/baike/pic/item/389aa8fdb7b8322e08244d3c.jpg");
			rivers.add(river);

			river = new River(
					"海河",
					73,
					"海河由于地势不高，每当海中涨潮时，河水倒流，落潮时河水顺流，和海水的潮汐相同，因此取名为“海”河。海河支流的永定河原名“无定河”，就是因为河道经常改变，只是皇帝为了祈愿它不再泛滥才人为改名为永定河。",
					"http://upload.wikimedia.org/wikipedia/commons/thumb/6/66/%E7%82%AB%E5%BD%A9%E6%B4%A5%E9%97%A899%E6%B5%B7%E6%B2%B3%E5%A4%9C%E6%99%AF.jpg/800px-%E7%82%AB%E5%BD%A9%E6%B4%A5%E9%97%A899%E6%B5%B7%E6%B2%B3%E5%A4%9C%E6%99%AF.jpg");
			rivers.add(river);

			river = new River(
					"淮河",
					1000,
					"淮河发源于河南省桐柏山老鸦叉，东流经河南，安徽，江苏三省，淮河下游水分三路。主流通过三河闸，出三河，经宝应湖、高邮湖在三江营入长江，是为入江水道，至此全长约1,000公里；另一路在洪泽湖东岸出高良涧闸，经苏北灌溉总渠在扁担港入黄海；第三路在洪泽湖东北岸出二河闸，经淮沭河北上连云港市，经临洪口注入海州湾。2003年开通淮河入海水道，自二河闸下游，紧贴苏北灌溉总渠北岸入海。",
					"http://imgsrc.baidu.com/baike/pic/item/d8b8c92a9d091b69d42af1b2.jpg");
			rivers.add(river);

			river = new River(
					"黑龙江",
					4444,
					"发源于蒙古国肯特山东麓，在石勒喀河与额尔古纳河交汇处形成。经过中国黑龙江省北界与俄罗斯哈巴罗夫斯克边疆区东南界，流入鄂霍次克海鞑靼海峡。黑龙江是中国三大河流之一、世界十大河之一（有些资料计为第六）。黑龙江本是中国的内河，19世纪中后期沙俄强行占领中国黑龙江以北、乌苏里江以东大片领土之后，才成为中俄界河。2004年，中国和俄罗斯签署最后边界协定，将两国国界以黑龙江为基本界限划清。",
					"http://imgsrc.baidu.com/baike/pic/item/d56b36344a36c27a5ab5f554.jpg");
			rivers.add(river);

			river = new River(
					"珠江",
					2400,
					"原指广州到入海口的一段河道，后来逐渐成为西江、北江、东江和珠江三角洲诸河的总称。其干流西江发源于云南省东北部沾益县的马雄山，干流流经云南、贵州、广西、广东四省（自治区）及香港、澳门特别行政区。",
					"http://upload.wikimedia.org/wikipedia/commons/a/a3/Pearl_river%2C_Guangzhou.JPG");
			rivers.add(river);
		}

		@Override
		public void onCreate(SQLiteDatabase database) {
			initRiverList();
			database.execSQL("create table if not exists rivers("
					+ " _id integer primary key autoincrement," + " name text,"
					+ "length integer," + " introduction text,"
					+ "image_url text" + ");");

			SQLiteStatement statement = database
					.compileStatement("insert into rivers(name,length,introduction,image_url) values(?,?,?,?)");

			for (River r : rivers) {
				int index = 1;
				statement.bindString(index++, r.getName());
				statement.bindLong(index++, r.getLength());
				statement.bindString(index++, r.getIntroduction());
				statement.bindString(index++, r.getImageUrl());
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
