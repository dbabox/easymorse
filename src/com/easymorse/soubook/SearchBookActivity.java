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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

public class SearchBookActivity extends Activity {
	private static String url = "http://api.douban.com/book/subject/isbn/";

	private Button returnButton;

	private Button addFavoriteButton;

	private WebView resultWeb;

	private BookInfo bookInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_result);

		try {
			bookInfo = getResultByIsbn(this.getIntent().getExtras().getString(
					"ISBN"));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		this.returnButton = (Button) this.findViewById(R.id.returnButton);
		this.returnButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SearchBookActivity.this.finish();
			}
		});

		this.addFavoriteButton = (Button) this
				.findViewById(R.id.favoriteButton);

		if (BookInfoDao.getInstance().get(bookInfo.getIsbn()) == null) {
			setAddFavorite();
		} else {
			setHasAddFavorite();
		}

		this.resultWeb = (WebView) this.findViewById(R.id.resultWeb);
		this.resultWeb.getSettings().setSupportZoom(false);
		this.resultWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(
				true);
		this.resultWeb.getSettings().setJavaScriptEnabled(true);

		this.resultWeb.loadUrl("file:///android_asset/results.html");

		this.resultWeb.addJavascriptInterface(new Object() {
			public String getBookName() {
				return bookInfo.getName();
			}

			public String getBookSummary() {
				return bookInfo.getSummary();
			}

			public String getBookImageUrl() {
				return bookInfo.getImageUrl();
			}

			public String getBookAuthor() {
				return bookInfo.getAuthor();
			}
		}, "searchResult");

		this.resultWeb.addJavascriptInterface(new Object() {
			public void save(String count) {
				finish();
			}

		}, "saveOrderCount");
	}
	
	private void setAddFavorite(){
		addFavoriteButton.setText("收藏");
		this.addFavoriteButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				BookInfoDao.getInstance().create(bookInfo);
				setHasAddFavorite();
			}
		});
	}

	private void setHasAddFavorite() {
		addFavoriteButton.setText("取消收藏");
		addFavoriteButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				BookInfoDao.getInstance().delete(bookInfo);
				setAddFavorite();
			}
		});
	}

	private BookInfo getResultByIsbn(String isbn)
			throws ClientProtocolException, IOException, IllegalStateException,
			XmlPullParserException {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url + isbn);
		HttpResponse response = client.execute(get);
		return getBookInfo(response.getEntity().getContent());
	}

	private BookInfo getBookInfo(InputStream inputStream)
			throws XmlPullParserException, IOException {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser parser = factory.newPullParser();
		parser.setInput(inputStream, "UTF-8");
		BookInfo bookInfo = new BookInfo();
		bookInfo.setIsbn(this.getIntent().getExtras().getString("ISBN"));

		for (int i = parser.getEventType(); i != XmlPullParser.END_DOCUMENT; i = parser
				.next()) {
			if (i == XmlPullParser.START_TAG
					&& parser.getName().equals("attribute")
					&& parser.getAttributeValue(0).equals("title")) {
				bookInfo.setName(parser.nextText());
				continue;
			}
			if (i == XmlPullParser.START_TAG
					&& parser.getName().equals("attribute")
					&& parser.getAttributeValue(0).equals("author")) {
				bookInfo.setAuthor(parser.nextText());
				continue;
			}
			if (i == XmlPullParser.START_TAG
					&& parser.getName().equals("summary")) {
				bookInfo.setSummary(parser.nextText());
				continue;
			}
			if (i == XmlPullParser.START_TAG && parser.getName().equals("link")) {
				if (parser.getAttributeValue(1).equals("image")) {
					bookInfo.setImageUrl(parser.getAttributeValue(0));
				}
				continue;
			}
		}

		return bookInfo;
	}
}
