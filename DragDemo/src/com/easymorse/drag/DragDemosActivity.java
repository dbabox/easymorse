package com.easymorse.drag;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

public class DragDemosActivity extends Activity {

	private FrameLayout board;

	private View sideView;

	private GestureDetector gestureDetector = new GestureDetector(
			new OnGestureListener() {

				@Override
				public boolean onDown(MotionEvent e) {
					return false;
				}

				@Override
				public boolean onFling(MotionEvent e1, MotionEvent e2,
						float velocityX, float velocityY) {
					Log.d("drag", "fling");
					return true;
				}

				@Override
				public void onLongPress(MotionEvent e) {
				}

				@Override
				public boolean onScroll(MotionEvent e1, MotionEvent e2,
						float distanceX, float distanceY) {
					Log.d("drag", "scroll:" + distanceX);
					FrameLayout.LayoutParams par = (LayoutParams) pawn
							.getLayoutParams();
					par.leftMargin = (int) (e2.getRawX() - e1.getRawX());
					pawn.setLayoutParams(par);

					par = (LayoutParams) sideView.getLayoutParams();
					par.leftMargin = (int) (e2.getRawX() - e1.getRawX()) + 480;
					sideView.setLayoutParams(par);
					return true;
				}

				@Override
				public void onShowPress(MotionEvent e) {
				}

				@Override
				public boolean onSingleTapUp(MotionEvent e) {
					return false;
				}

			});

	private View pawn;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		pawn = findViewById(R.id.Pawn);
		pawn.setOnTouchListener(dragt);

		board = (FrameLayout) findViewById(R.id.Board);

		sideView = findViewById(R.id.SideView);
		LayoutParams params = (LayoutParams) sideView.getLayoutParams();
		params.leftMargin = 480;
		sideView.setLayoutParams(params);

	}

	OnTouchListener dragt = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (v.getId()) {// What is being touched
			case R.id.Pawn: {// Which action is being taken
				switch (event.getAction()) {
				case MotionEvent.ACTION_MOVE: {
					gestureDetector.onTouchEvent(event);
					break;
				}// inner case MOVE
				case MotionEvent.ACTION_UP: {
					gestureDetector.onTouchEvent(event);
					// TODO animation
					break;
				}// inner case UP
				case MotionEvent.ACTION_DOWN: {
					gestureDetector.onTouchEvent(event);
					break;
				}// inner case UP
				}// inner switch
				break;
			}// case pawn
			}// switch
			return true;
		}// onTouch
	};// dragt Ï
}