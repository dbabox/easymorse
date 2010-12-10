package com.easymorse;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class SwitchViewsActivity extends Activity {

	private static final String TAG = "switchview";

	private FrameLayout viewSwitcher;

	private int currentIndex;

	private ImageView currentImageView;

	private ViewGroup pageControl;

	private GestureDetector gestureDetector = new GestureDetector(
			new OnGestureListener() {

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
					Log.d(TAG, "fling...");
					Log.d(TAG, "current index:" + currentIndex);
					ImageView imageView = null;
					if (e1.getX() < e2.getX()) {
						Log.d(TAG, "to right");
						imageView = getPreviousImageView();
						if (imageView != null) {
							viewSwitcher.removeView(currentImageView);
							viewSwitcher.addView(imageView);
							currentImageView = imageView;
						}
					} else {
						Log.d(TAG, "to left");
						imageView = getNextImageView();
					}

					if (imageView != null) {
						viewSwitcher.removeView(currentImageView);
						viewSwitcher.addView(imageView);
						currentImageView = imageView;
						generatePageControl();
					}

					return true;
				}

				@Override
				public boolean onDown(MotionEvent e) {
					// TODO Auto-generated method stub
					return false;
				}
			});

	private List<Integer> drawableIds = Arrays.asList(new Integer[] {
			R.drawable.a1, R.drawable.a2, R.drawable.a3 });

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		this.viewSwitcher = (FrameLayout) this.findViewById(R.id.viewSwitcher);
		this.viewSwitcher.addView(getCurrentImageView());

		this.pageControl = (ViewGroup) this.findViewById(R.id.pageControl);
		this.generatePageControl();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return this.gestureDetector.onTouchEvent(event);
	}

	private ImageView getCurrentImageView() {
		if (currentImageView == null) {
			this.currentImageView = new ImageView(this);
			this.currentImageView.setBackgroundDrawable(getResources()
					.getDrawable(drawableIds.get(currentIndex)));
		}
		return this.currentImageView;
	}

	private ImageView getNextImageView() {
		if (this.currentIndex < this.drawableIds.size() - 1) {
			Log.d(TAG, "get next view");
			ImageView imageView = new ImageView(this);
			currentIndex++;
			imageView.setBackgroundDrawable(getResources().getDrawable(
					drawableIds.get(currentIndex)));
			return imageView;
		}
		return null;
	}

	private ImageView getPreviousImageView() {
		if (this.currentIndex > 0) {
			Log.d(TAG, "get previous view");
			ImageView imageView = new ImageView(this);
			currentIndex--;
			imageView.setBackgroundDrawable(getResources().getDrawable(
					drawableIds.get(currentIndex)));
			return imageView;
		}
		return null;
	}

private void generatePageControl() {
	pageControl.removeAllViews();

	for (int i = 0; i < drawableIds.size(); i++) {
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