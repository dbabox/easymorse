package com.easymorse.list;

import java.io.IOException;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;

import com.thoughtworks.xstream.XStream;

public class ListViewDemoActivity extends ListActivity {
	
	private MyImageAndTextListAdapter listAdapter;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		this.setListAdapter(getAdapter());
	}
	
	private MyImageAndTextListAdapter getAdapter(){
		if(this.listAdapter==null){
			listAdapter=new MyImageAndTextListAdapter(this, getList());
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