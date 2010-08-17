package com.easymorse.grid.demo;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

public class GridDemoActivity extends ListActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		LayoutInflater layoutInflater = (LayoutInflater) this
				.getSystemService("layout_inflater");
		View headerView = layoutInflater.inflate(R.layout.list_header, null);
		setGridView(headerView);
		ListView listView = (ListView) this.findViewById(android.R.id.list);
		listView.addHeaderView(headerView);
		listView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, new String[] { "隋", "唐",
						"宋", "元", "明", "清" }));

		Message message = new Message();
		message.obj = (ProgressBar) headerView.findViewById(R.id.p1);
		;
		new Handler() {
			public void handleMessage(Message msg) {
				ProgressBar bar = (ProgressBar) msg.obj;
				bar.setVisibility(ProgressBar.GONE);
			}
		}.sendMessageDelayed(message, 1000 * 10);
	}

	private void setGridView(View view) {
		GridView gridView = (GridView) view.findViewById(R.id.grid);
		gridView.setNumColumns(10);

		ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();

		for (int i = 0; i < 10; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage", R.drawable.k);
			map.put("ItemText", "清.康熙" + "(" + i + ")");
			items.add(map);
		}

		SimpleAdapter adapter = new SimpleAdapter(this, items, R.layout.item,
				new String[] { "ItemImage", "ItemText" }, new int[] {
						R.id.ItemImage, R.id.ItemText });
		gridView.setAdapter(adapter);
	}
}