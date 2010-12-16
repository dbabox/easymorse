package com.easymorse.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class MyViewGroup extends ViewGroup {

	private static final String TAG = "scroller";

	private Scroller scroller;

	public Scroller getScroller() {
		return scroller;
	}

	public MyViewGroup(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.scroller = new Scroller(context);
	}

	public MyViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.scroller = new Scroller(context);
	}

	public MyViewGroup(Context context) {
		super(context);
		this.scroller = new Scroller(context);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		Log.d(TAG, ">>left: " + left + " top: " + top + " right: " + right
				+ " bottom:" + bottom);
		View child = getChildAt(0);
		child.setVisibility(View.VISIBLE);
		child.measure(right - left, bottom - top);
		child.layout(0, 0, 480, 800);
	}

	@Override
	public void computeScroll() {
		if (scroller.computeScrollOffset()) {
			scrollTo(scroller.getCurrX(), 0);
			postInvalidate();
		}
	}
}
