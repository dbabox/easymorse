package com.easymorse.soubook;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SouBookMenuActivity extends Activity {

	private static String url = "http://api.douban.com/book/subject/isbn/";

	private Button searchButton;

	private TextView searchResult;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		this.searchButton = (Button) this.findViewById(R.id.searchButton);
		this.searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(
						"com.google.zxing.client.android.SCAN");
				startActivityForResult(intent, 0);
			}
		});

		this.searchResult = (TextView) this.findViewById(R.id.searchResult);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode != 0) {
			return;
		}
		try {
			this.searchResult.setText(this.getResultByIsbn(data
					.getStringExtra("SCAN_RESULT")));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String getResultByIsbn(String isbn) throws ClientProtocolException,
			IOException, IllegalStateException, XmlPullParserException {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url + isbn);
		HttpResponse response = client.execute(get);
		return getBookInfo(response.getEntity().getContent());
	}

	private String getBookInfo(InputStream inputStream)
			throws XmlPullParserException, IOException {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser parser = factory.newPullParser();
		parser.setInput(inputStream, "UTF-8");
		StringBuilder builder = new StringBuilder();

		for (int i = parser.getEventType(); i != XmlPullParser.END_DOCUMENT; i = parser
				.next()) {
			if (i == XmlPullParser.START_TAG
					&& parser.getName().equals("title")) {
				builder.append("书名: ").append(parser.nextText());
				break;
			}
		}

		return builder.toString();
	}
}