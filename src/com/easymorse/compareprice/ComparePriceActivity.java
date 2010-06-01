package com.easymorse.compareprice;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ComparePriceActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		TextView textView = (TextView) this.findViewById(R.id.priceText);
		try {
			textView.setText(this.getComparePrice());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private String getComparePrice() throws Exception {
		StringBuilder builder = new StringBuilder();
		long time = System.currentTimeMillis();
		builder.append(">>>");

		Parser parser = new Parser(
				"http://book.douban.com/subject/4803982/buylinks?sortby=price");
		NodeList nodeList = parser.parse(new AndFilter(new NodeFilter[] {
				new TagNameFilter("a"),
				new HasAttributeFilter("target", "_blank") }));

		for (int i = 0; i < nodeList.size(); i++) {
			if (i > 1) {
				builder.append(nodeList.elementAt(i).getFirstChild().getText()).append("|");
			}
		}

		builder.append("耗时: " + (System.currentTimeMillis() - time));
		return builder.toString();
	}
}