package com.easymorse.listview;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * river详细信息activity
 * 
 * @author marshal
 * 
 */
public class DetailViewActivity extends Activity {

	ImageView imageView;

	ProgressBar imageLoadProgressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);

		long id = getIntent().getExtras().getLong(GlobalValues.RIVER_ID);
		Log.d("list", "id>>>" + id);

		Cursor cursor = managedQuery(ContentUris.withAppendedId(
				RiverContentProvider.CONTENT_URI, id), new String[] {
				RiverContentProvider.NAME, RiverContentProvider.INTRODUCTION,
				RiverContentProvider.IMAGE_URL }, null, null, null);
		cursor.moveToNext();
		setTitle(cursor.getString(cursor
				.getColumnIndex(RiverContentProvider.NAME)));
		TextView textView = (TextView) findViewById(R.id.riverIntroduction);
		textView.setText(cursor.getString(cursor
				.getColumnIndex(RiverContentProvider.INTRODUCTION)));

		imageView = (ImageView) findViewById(R.id.riverImage);
		imageLoadProgressBar = (ProgressBar) findViewById(R.id.riverImageProgress);

		new ShowImageTask().execute(cursor.getString(cursor
				.getColumnIndex(RiverContentProvider.IMAGE_URL)), "" + id);
	}

private class ShowImageTask extends AsyncTask<String, Void, Void> {

	private String fileName;

	@Override
	protected Void doInBackground(String... params) {
		fileName = params[1] + ".jpg";

		File cacheFile = new File(getCacheDir(), fileName);
		if (!cacheFile.exists()) {
			//创建临时文件，用于下载图片使用
			File tempFile = new File(getCacheDir(), "tmp");
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(params[0]);
			try {
				HttpResponse response = client.execute(get);
				FileOutputStream outputStream = new FileOutputStream(
						tempFile);
				InputStream inputStream = response.getEntity().getContent();
				for (int i = inputStream.read(); i != -1; i = inputStream
						.read()) {
					outputStream.write(i);
				}
				inputStream.close();
				outputStream.close();
				
				/**
				 * 改名临时文件，改为对应河流id的jpg文件
				 * 这样做是为了防止图片下载不全的问题
				 */
				tempFile.renameTo(cacheFile);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		try {
			imageView.setImageDrawable(Drawable.createFromStream(
					new FileInputStream(new File(getCacheDir(), fileName)),
					"river.jpg"));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		imageLoadProgressBar.setVisibility(View.GONE);
	}
	}
}
