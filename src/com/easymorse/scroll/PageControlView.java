package com.easymorse.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.easymorse.scroll.MyViewGroup.ScrollToScreenCallback;

public class PageControlView extends LinearLayout implements
		ScrollToScreenCallback {

	private int count;

	private Context context;

	public void setCount(int count) {
		this.count = count;
	}

	public PageControlView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.init(context);
	}

	public PageControlView(Context context) {
		super(context);
		this.init(context);
	}

	private void init(Context context) {
		this.context=context;
	}

	@Override
	public void callback(int currentIndex) {
		generatePageControl(currentIndex);
	}

	public void generatePageControl(int currentIndex) {
		this.removeAllViews();

		for (int i = 0; i < this.count; i++) {
			ImageView imageView = new ImageView(context);
			if (currentIndex == i) {
				imageView.setImageResource(R.drawable.page_indicator_focused);
			} else {
				imageView.setImageResource(R.drawable.page_indicator);
			}
			this.addView(imageView);
		}
	}
}
