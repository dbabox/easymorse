package com.easymorse;

/**
 * RelativeLayout 允许子元素指定他们相对于其它元素或父元素的位置（通过ID 指定）。
 * 因此，你可以以右对齐，或上下，或置于屏幕中央的形式来 排列两个元素。元素按顺序排列，
 * 因此如果第一个元素在屏幕的中央，那么相对于这个元素的其它元素将以屏幕中央的相对位置来排列。
 * 如果使用XML 来指定这个 layout ，在你定义它之前，被关联的元素必须定义。
 */
import android.app.Activity;
import android.os.Bundle;

public class RelativeLayout extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.relativelayout);
	}

}