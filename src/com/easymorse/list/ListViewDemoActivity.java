package com.easymorse.list;

import java.io.IOException;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.thoughtworks.xstream.XStream;

public class ListViewDemoActivity extends ListActivity {

	private MyImageAndTextListAdapter listAdapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		try {
			this.setWeatherInfo((TextView) this.findViewById(R.id.weatherText));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		this.setListAdapter(getAdapter());
	}

	private void setWeatherInfo(TextView textView)
			throws XmlPullParserException, IOException {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser parser = factory.newPullParser();
		parser.setInput(this.getAssets().open("weather.xml"), "UTF-8");

		for (int i = parser.getEventType(); i != XmlPullParser.END_DOCUMENT; i = parser
				.next()) {
			if (i == XmlPullParser.START_TAG
					&& parser.getName().equals("current_conditions")) {
				StringBuilder builder = new StringBuilder();

				for (int j = parser.getEventType();; j = parser.next()) {
					if (j == XmlPullParser.END_TAG
							&& parser.getName().equals("current_conditions")) {
						break;
					}
					if (j == XmlPullParser.START_TAG) {
						if (parser.getName().equals("temp_c")) {
							builder.append("当前气温：").append(
									parser.getAttributeValue(0)).append("摄氏度");
						}
						if(parser.getName().equals("wind_condition")){
							builder.append(", ").append(parser.getAttributeValue(0));
						}
					}
				}

				textView.setText(builder.toString());
			}
		}
	}

	private MyImageAndTextListAdapter getAdapter() {
		if (this.listAdapter == null) {
			listAdapter = new MyImageAndTextListAdapter(this, getList());
		}
		return listAdapter;
	}

	@SuppressWarnings("unchecked")
	private List<NewsBean> getList() {
		try {
			XStream xStream = new XStream();
			xStream.alias("item", NewsBean.class);
			return (List<NewsBean>) xStream.fromXML(this.getAssets().open(
					"list.xml"));

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}