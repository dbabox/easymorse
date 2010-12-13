package com.easymorse.switcher;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class ViewSwitcherActivity extends Activity {

	private static final int FLING_MIN_DISTANCE = 120,
			FLING_MIN_VELOCITY = 200;

	private static final int[] IMAGES = new int[] { R.drawable.a1,
			R.drawable.a2, R.drawable.a3, R.drawable.a4 };

	private ViewFlipper viewFlipper;

	private int currentIndex;

	private GestureDetector detector;

	private ViewGroup pageControl;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		this.viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
		View view = new View(this);
		view.setBackgroundResource(IMAGES[currentIndex]);
		this.viewFlipper.addView(view);

		this.pageControl = (ViewGroup) this.findViewById(R.id.pageControl);
		this.generatePageControl();

		this.detector = new GestureDetector(new OnGestureListener() {

			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onShowPress(MotionEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2,
					float distanceX, float distanceY) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onLongPress(MotionEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE) {
					if (currentIndex < IMAGES.length - 1) {
						currentIndex++;
						View view = new View(ViewSwitcherActivity.this);
						view.setBackgroundResource(IMAGES[currentIndex]);
						viewFlipper.addView(view);

						viewFlipper.setInAnimation(AnimationUtils
								.loadAnimation(ViewSwitcherActivity.this,
										R.anim.push_left_in));
						viewFlipper.setOutAnimation(AnimationUtils
								.loadAnimation(ViewSwitcherActivity.this,
										R.anim.push_left_out));

						viewFlipper.showNext();
					}
					generatePageControl();
					return true;
				} else if (e1.getX() - e2.getX() < -FLING_MIN_DISTANCE) {
					if (currentIndex > 0) {
						currentIndex--;
						viewFlipper.setInAnimation(AnimationUtils
								.loadAnimation(ViewSwitcherActivity.this,
										R.anim.push_right_in));
						viewFlipper.setOutAnimation(AnimationUtils
								.loadAnimation(ViewSwitcherActivity.this,
										R.anim.push_right_out));
						viewFlipper.showPrevious();
					}
					generatePageControl();
					return true;
				}
				return false;
			}

			@Override
			public boolean onDown(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return this.detector.onTouchEvent(event);
	}

	private void generatePageControl() {
		pageControl.removeAllViews();

		for (int i = 0; i < IMAGES.length; i++) {
			ImageView imageView = new ImageView(this);
			if (currentIndex == i) {
				imageView.setImageResource(R.drawable.page_indicator_focused);
			} else {
				imageView.setImageResource(R.drawable.page_indicator);
			}
			this.pageControl.addView(imageView);
		}
	}
}