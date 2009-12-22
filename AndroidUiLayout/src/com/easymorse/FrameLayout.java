package com.easymorse;

import android.app.Activity;
import android.os.Bundle;

/**
 * 
 * @author ubuntu
 *FrameLayout是最简单的布局格式，所有的元素将锁定到屏幕的左上角
 */
public class FrameLayout extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.framelayout);
    }
}
