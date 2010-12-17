package com.easymorse.scroll;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

public class ScrollDemosActivity extends Activity {

	@SuppressWarnings("unused")
	private static final String TAG = "scroller";

	private GestureDetector gestureDetector;

	private MyViewGroup viewGroup;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		viewGroup = new MyViewGroup(this);
		setContentView(viewGroup);

		// ImageView imageView = new ImageView(this);
		// imageView.setImageDrawable(getResources().getDrawable(R.drawable.a1));
		// viewGroup.addView(imageView);

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

		// ViewConfiguration configuration = ViewConfiguration.get(this);
		// Toast.makeText(
		// this,
		// "touch slop:" + configuration.getScaledTouchSlop()
		// + " tap timeout:" + configuration.getTapTimeout(), 1000)
		// .show();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		gestureDetector.onTouchEvent(event);

		// if (event.getAction() == MotionEvent.ACTION_MOVE) {
		// VelocityTracker tracker = VelocityTracker.obtain();
		// tracker.addMovement(event);
		// tracker.computeCurrentVelocity(1);
		// Toast.makeText(ScrollDemosActivity.this,
		// " tracker:" + tracker.getXVelocity(), 1000).show();
		// }

		if (event.getAction() == MotionEvent.ACTION_UP) {
			// if (Math.abs(scrollPosition) < 480 / 2) {
			viewGroup.scrollTo(0, 0);
			// } else if (scrollPosition < 0) {
			// viewGroup.scrollTo(480, 0);
			// }
		}

		return true;
	}
}