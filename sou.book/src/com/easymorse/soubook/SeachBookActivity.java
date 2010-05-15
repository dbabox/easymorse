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
import android.os.Bundle;
import android.widget.TextView;

public class SeachBookActivity extends Activity {
	private static String url = "http://api.douban.com/book/subject/isbn/";

	private TextView resultText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_result);

		resultText = (TextView) this.findViewById(R.id.resultText);
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
