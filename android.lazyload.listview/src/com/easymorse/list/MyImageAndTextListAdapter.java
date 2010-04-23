package com.easymorse.list;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.easymorse.list.AsyncImageLoader.ImageCallback;

public class MyImageAndTextListAdapter extends ArrayAdapter<NewsBean> {

	public MyImageAndTextListAdapter(Activity activity,
			List<NewsBean> newsList) {
		super(activity, 0, newsList);
	}

	private AsyncImageLoader imageLoader = new AsyncImageLoader();

	private Map<Integer, View> viewMap = new HashMap<Integer, View>();

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = this.viewMap.get(position);

		if (rowView == null) {
			LayoutInflater inflater = ((Activity) this.getContext())
					.getLayoutInflater();
			rowView = inflater.inflate(R.layout.news_row, null);
			NewsBean newsBean = this.getItem(position);

			TextView textView = (TextView) rowView.findViewById(R.id.title);
			textView.setText(newsBean.getTitle());

			final ImageView imageView = (ImageView) rowView
					.findViewById(R.id.image);
			imageLoader.loadDrawable(newsBean.getImage(), new ImageCallback() {

				public void imageLoaded(Drawable imageDrawable, String imageUrl) {
					imageView.setImageDrawable(imageDrawable);
				}
			});
			viewMap.put(position, rowView);
		}
		return rowView;
	}

}
