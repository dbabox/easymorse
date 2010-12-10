package demos.animation;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;

public class AnimationDemosActivity extends Activity implements
		OnGestureListener {

	private GestureDetector gestureDetector;

	private View view;

	private TranslateAnimation barUpAnimation, barDownAnimation;

	private boolean isUp = true;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.gestureDetector = new GestureDetector(this);
		setContentView(R.layout.main);
		Log.d("anim", "width>>"
				+ this.getWindow().getWindowManager().getDefaultDisplay()
						.getWidth()
				+ " height>>"
				+ this.getWindow().getWindowManager().getDefaultDisplay()
						.getHeight());

		this.view = this.findViewById(R.id.barView);

		barUpAnimation = new TranslateAnimation(0, 0, 0, -100);
		barUpAnimation.setDuration(700);
		barUpAnimation.setFillBefore(true);
		barUpAnimation.setFillAfter(true);
		barUpAnimation.setInterpolator(new MyInterpolator());

		barDownAnimation = new TranslateAnimation(0, 0, -100, 0);
		barDownAnimation.setDuration(700);
		barDownAnimation.setFillBefore(true);
		barDownAnimation.setFillAfter(true);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return this.gestureDetector.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		Log.d("anim", "long pressed.");
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		Log.d("anim", ">>scroll..." + distanceX + "  " + distanceY);
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		Log.d("anim", ">>show press");
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		Log.d("anim", "tap up.");

		if (isUp) {
			this.view.startAnimation(barUpAnimation);
			isUp = false;
		} else {
			this.view.startAnimation(barDownAnimation);
			isUp = true;
		}

		return true;
	}
}