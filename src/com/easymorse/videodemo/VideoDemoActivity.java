package com.easymorse.videodemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class VideoDemoActivity extends Activity {

	ListView listView;

	Gallery gallery;

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
		this.gallery = (Gallery) this.findViewById(R.id.gallery);
		this.gallery.setAdapter(new ImageAdapter(this));
		this.gallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				Toast.makeText(VideoDemoActivity.this, "---",
						Toast.LENGTH_SHORT).show();
			}
		});

		listView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, new String[] {
						"a", "b", "c", "d", "e", "f", "g" }));
	}
}

class ImageAdapter extends BaseAdapter {

	public ImageAdapter(Context context) {
		super();
		this.context = context;
	}

	private Context context;

	@Override
	public int getCount() {
		return 7;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout layout = (LinearLayout) View.inflate(context,
				R.layout.image_item, null);
		return layout;
	}

}