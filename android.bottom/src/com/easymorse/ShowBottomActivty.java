package com.easymorse;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class ShowBottomActivty extends Activity {
	private LinearLayout linearLayout, publish, change, more;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);

		linearLayout = (LinearLayout) findViewById(R.id.home);
		linearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				linearLayout
						.setBackgroundResource(R.drawable.tab_two_highlight);
				publish.setBackgroundResource(R.drawable.tab_one_normal);
				change.setBackgroundResource(R.drawable.tab_one_normal);
				more.setBackgroundResource(R.drawable.tab_one_normal);
			}
		});
		linearLayout
		.setBackgroundResource(R.drawable.tab_two_highlight);

		publish = (LinearLayout) findViewById(R.id.publish);
		publish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				publish.setBackgroundResource(R.drawable.tab_two_highlight);
				linearLayout.setBackgroundResource(R.drawable.tab_one_normal);
				change.setBackgroundResource(R.drawable.tab_one_normal);
				more.setBackgroundResource(R.drawable.tab_one_normal);
			}
		});

		change = (LinearLayout) findViewById(R.id.change);
		change.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				change.setBackgroundResource(R.drawable.tab_two_highlight);
				linearLayout.setBackgroundResource(R.drawable.tab_one_normal);
				publish.setBackgroundResource(R.drawable.tab_one_normal);
				more.setBackgroundResource(R.drawable.tab_one_normal);
			}
		});

		more = (LinearLayout) findViewById(R.id.more);
		more.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				more.setBackgroundResource(R.drawable.tab_two_highlight);
				linearLayout.setBackgroundResource(R.drawable.tab_one_normal);
				publish.setBackgroundResource(R.drawable.tab_one_normal);
				change.setBackgroundResource(R.drawable.tab_one_normal);
			}
		});
	}
}