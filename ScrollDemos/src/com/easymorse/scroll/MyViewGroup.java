package com.easymorse.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Scroller;

public class MyViewGroup extends ViewGroup {

	private static final String TAG = "scroller";

	private Scroller scroller;

	private int currentScreenIndex;

	public Scroller getScroller() {
		return scroller;
	}

	public MyViewGroup(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	public MyViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public MyViewGroup(Context context) {
		super(context);
		initView(context);
	}

	private void initView(Context context) {
		this.scroller = new Scroller(context);

		ImageView imageView = new ImageView(context);
		imageView.setImageDrawable(getResources().getDrawable(R.drawable.a1));
		this.addView(imageView);

		imageView = new ImageView(context);
		imageView.setImageDrawable(getResources().getDrawable(R.drawable.a2));
		this.addView(imageView);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		Log.d(TAG, ">>left: " + left + " top: " + top + " right: " + right
				+ " bottom:" + bottom);

		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			child.setVisibility(View.VISIBLE);
			child.measure(right - left, bottom - top);
			child.layout(0 + i * 480, 0, 480 + i * 480, 800);
		}
	}

	@Override
	public void computeScroll() {
		if (scroller.computeScrollOffset()) {
			scrollTo(scroller.getCurrX(), 0);
			postInvalidate();
		}
	}
}
