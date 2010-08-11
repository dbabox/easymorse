package com.easymorse.textbutton;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class TextButton extends TextView {

	public TextButton(Context context) {
		super(context);
	}

	public TextButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public TextButton(final Context context, AttributeSet attrs) {
		this(context, attrs, 0);

		this.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_CANCEL
						|| event.getAction() == MotionEvent.ACTION_UP
						|| event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					Toast.makeText(context, "touched", Toast.LENGTH_SHORT).show();
				}
				return false;
			}
		});
	}

}
