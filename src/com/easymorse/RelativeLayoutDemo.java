package com.easymorse;

import android.app.Activity;
import android.os.Bundle;

public class RelativeLayoutDemo extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.relative_layout);//NOTE: 布局文件名称要小写
	}
}
