package com.easymorse.scroll;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.ImageView;

public class ScrollDemosActivity extends Activity {

	@SuppressWarnings("unused")
	private static final String TAG = "scroller";

	private GestureDetector gestureDetector;

	private MyViewGroup viewGroup;

	// private int scrollPosition;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		viewGroup = new MyViewGroup(this);
		setContentView(viewGroup);

		ImageView imageView = new ImageView(this);
		imageView.setImageDrawable(getResources().getDrawable(R.drawable.a1));
		viewGroup.addView(imageView);

		gestureDetector = new GestureDetector(new OnGestureListener() {

			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				return false;
			}

			@Override
			public void onShowPress(MotionEvent e) {
			}

			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2,
					float distanceX, float distanceY) {
				viewGroup.scrollBy((int) distanceX, 0);
				// scrollPosition = (int) (e2.getRawX() - e1.getRawX());
				return true;
			}

			@Override
			public void onLongPress(MotionEvent e) {
			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				return false;
			}

			@Override
			public boolean onDown(MotionEvent e) {
				return false;
			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		gestureDetector.onTouchEvent(event);

		if (event.getAction() == MotionEvent.ACTION_UP) {
			viewGroup.scrollTo(0, 0);
		}

		return true;
	}
}