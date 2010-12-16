package com.easymorse.scroll;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class ScrollDemosActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyViewGroup viewGroup=new MyViewGroup(this);
		setContentView(viewGroup);
		
		ImageView imageView=new ImageView(this);
		imageView.setImageDrawable(getResources().getDrawable(R.drawable.a1));
		viewGroup.addView(imageView);
		
		viewGroup.getScroller().startScroll(0, 0, 480, 0,10000);
	}
}