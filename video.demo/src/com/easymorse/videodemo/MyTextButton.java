package com.easymorse.videodemo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class MyTextButton extends Button {

	private boolean checked;

	public MyTextButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean performClick() {
		this.checked = !this.checked;
		return super.performClick();
	}

	@Override
	protected int[] onCreateDrawableState(int extraSpace) {
		if (!checked) {
			return Button.PRESSED_ENABLED_SELECTED_STATE_SET;
		} else {
			return Button.EMPTY_STATE_SET;
		}
	}
}
