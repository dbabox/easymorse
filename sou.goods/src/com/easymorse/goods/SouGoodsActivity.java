package com.easymorse.goods;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SouGoodsActivity extends Activity {

	private Button searchButton;

	private TextView textView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		this.textView = (TextView) this.findViewById(R.id.goodsInfo);

		this.searchButton = (Button) this.findViewById(R.id.searchButton);
		this.searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(
						"com.google.zxing.client.android.SCAN");
				startActivityForResult(intent, 0);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode != 0) {
			return;
		}
		StringBuilder builder = new StringBuilder();
		builder.append("商品信息: ");

		try {
			Parser parser = new Parser(
					"http://www.anccnet.com/searchResult.aspx?keyword="
							+ data.getExtras().getString("SCAN_RESULT"));
			NodeList nodeList = parser.parse(new TagNameFilter("dd"));
			for (int i = 0; i < nodeList.size(); i++) {
				if (i == 1 || i == 2) {
					builder.append(
							nodeList.elementAt(i).getFirstChild()
									.getFirstChild().getText().trim()).append(" | ");
				} else {
					builder.append(
							nodeList.elementAt(i).getFirstChild().getText())
							.append(" | ");
				}
			}

		} catch (ParserException e) {
			throw new RuntimeException(e);
		}

		this.textView.setText(builder.toString());
	}
}