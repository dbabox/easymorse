package com.easymorse;

/**
 * AbsoluteLayout 可以让子元素指定准确的x/y坐标值，并显示在屏幕上。(0, 0)为左上角，
 * 当向下或向右移动时，坐标值将变大。AbsoluteLayout 没有页边框，
 * 允许元素之间互相重叠（尽管不推荐）。我们通常不推荐使用 AbsoluteLayout ，
 * 除非你有正当理由要使用它，因为它使界面代码太过刚性，以至于在不同的设备上可能不能很好地工作。
 */
import android.app.Activity;
import android.os.Bundle;

public class AbsoluteLayout extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.absolutelayout);
	}

}
