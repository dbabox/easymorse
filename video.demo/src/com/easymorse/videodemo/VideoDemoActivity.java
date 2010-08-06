package com.easymorse.videodemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableRow;

public class VideoDemoActivity extends Activity {

	ListView listView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); // 声明使用自定义标题
		setContentView(R.layout.main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);// 自定义布局赋值
		listView = (ListView) this.findViewById(R.id.list);
		listView.addHeaderView(LayoutInflater.from(this).inflate(
				R.layout.table_title, null));
		TableRow row = (TableRow) this.findViewById(R.id.row);
		for (int i = 0; i < 5; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setImageResource(R.drawable.k);
			row.addView(imageView);
		}
		listView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, new String[] {
						"a", "b", "c", "d", "e", "f", "g" }));
	}
}