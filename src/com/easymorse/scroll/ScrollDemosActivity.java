package com.easymorse.scroll;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class ScrollDemosActivity extends Activity {

	@SuppressWarnings("unused")
	private static final String TAG = "scroller";

	private MyViewGroup viewGroup;

	private int[] drawableResources = new int[] { R.drawable.a1, R.drawable.a2,
			R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a6 };

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		viewGroup = new MyViewGroup(this);
		setContentView(viewGroup);

		for (int drawableId : drawableResources) {
			ImageView imageView = new ImageView(this);
			imageView.setImageDrawable(getResources().getDrawable(drawableId));
			viewGroup.addView(imageView);
		}
	}
}