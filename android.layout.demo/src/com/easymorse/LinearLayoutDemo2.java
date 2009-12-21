package com.easymorse;

import android.app.Activity;
import android.os.Bundle;

public class LinearLayoutDemo2 extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.lineardemo2);// NOTE: layout文件只认第一个句点前的部分。
	}
}
