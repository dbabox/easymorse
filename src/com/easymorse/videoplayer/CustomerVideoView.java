package com.easymorse.videoplayer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class CustomerVideoView extends VideoView {

	private static String TAG = "customer.videoplayer";

	public CustomerVideoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CustomerVideoView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomerVideoView(Context context) {
		super(context);
	}

}
