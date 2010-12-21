package com.easymorse.scroll;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ScrollDemosActivity extends Activity {

	@SuppressWarnings("unused")
	private static final String TAG = "scroller";

	private MyViewGroup viewGroup;

	private PageControlView pageControl;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		viewGroup = (MyViewGroup) findViewById(R.id.myViewGroup);

		ImageView imageView = new ImageView(this);
		imageView.setImageDrawable(getResources().getDrawable(R.drawable.a1));
		viewGroup.addView(imageView);

		viewGroup.addView(View.inflate(this, R.layout.form, null));

		imageView = new ImageView(this);
		imageView.setImageDrawable(getResources().getDrawable(R.drawable.a2));
		viewGroup.addView(imageView);

		pageControl=(PageControlView) findViewById(R.id.pageControl);
		pageControl.setCount(viewGroup.getChildCount());
		pageControl.generatePageControl(0);
		viewGroup.setScrollToScreenCallback(pageControl);
	}

	
}