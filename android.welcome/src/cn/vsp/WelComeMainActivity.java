package cn.vsp;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;

public class WelComeMainActivity extends TabActivity {

	private static String TAG = "welcome";

	private String userName;

	private Drawable image;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		final TabHost tabHost = getTabHost();

		tabHost.addTab(tabHost.newTabSpec("landscapes").setIndicator("风景",
				getResources().getDrawable(R.drawable.a2)).setContent(
				new TabContentFactory() {

					@Override
					public View createTabContent(String tag) {
						return new ListView(WelComeMainActivity.this);
					}
				}));

		tabHost.addTab(tabHost.newTabSpec("restrant").setIndicator("餐馆")
				.setContent(new TabContentFactory() {

					@Override
					public View createTabContent(String tag) {
						return new ListView(WelComeMainActivity.this);
					}
				}));

		tabHost.addTab(tabHost.newTabSpec("hotel").setIndicator("酒店")
				.setContent(new TabContentFactory() {

					@Override
					public View createTabContent(String tag) {
						return new ListView(WelComeMainActivity.this);
					}
				}));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "更新数据").setIcon(R.drawable.download);
		menu.add(0, 2, 0, "关于").setIcon(R.drawable.about);
		menu.add(0, 3, 0, "退出").setIcon(R.drawable.exit);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.i(TAG, ">>" + item.getItemId());
		switch (item.getItemId()) {
		case 1:
			downloadData();
			return true;
		case 2:
			return true;
		case 3:
			this.finish();
			return true;
		}
		return false;
	}

	private void downloadData() {
		final ProgressDialog dialog = ProgressDialog.show(this, "",
				"下载数据，请稍等 ...", true, true);
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet("http://mp.myvsp.cn/data/welcome.db");

				try {
					HttpResponse response = client.execute(get);

					String fileName = "mydb";
					int bufferSize = 1024 * 10;

					if (getFileStreamPath(fileName).exists()) {
						getFileStreamPath(fileName).delete();
						Log.i(TAG, "delete old mydb file.");
					}

					FileOutputStream outputStream = openFileOutput("mydb", 0);
					InputStream inputStream = response.getEntity().getContent();

					byte[] data = new byte[bufferSize];

					for (int i = inputStream.read(data); i > 0; i = inputStream
							.read(data)) {
						outputStream.write(data, 0, i);
					}

					Log.i(TAG, "get ok, file size: "
							+ getFileStreamPath(fileName).length());

					SQLiteDatabase database = SQLiteDatabase
							.openOrCreateDatabase(getFileStreamPath(fileName)
									.getAbsolutePath(), null);
					Cursor cursor = database.rawQuery(
							"select name,image from users", new String[] {});

					if (cursor.moveToFirst()) {
						userName = cursor.getString(0);
						ByteArrayInputStream stream = new ByteArrayInputStream(
								cursor.getBlob(1));
						image = Drawable.createFromStream(stream, "image");
						Log.i(TAG, "user name: " + userName);
					}
					database.close();

				} catch (Exception e) {
					throw new RuntimeException(e);
				}

				dialog.dismiss();

				confirmHandler.post(new Runnable() {
					@Override
					public void run() {
						AlertDialog.Builder builder = new Builder(
								WelComeMainActivity.this);
						builder.setMessage("数据更新完毕。用户名：" + userName);
						builder.setTitle("提示");
						builder.setIcon(image);
						builder.setPositiveButton("确认", new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});
						builder.create().show();
					}
				});
			}
		});
		t.start();
	}

	Handler confirmHandler = new Handler();
}