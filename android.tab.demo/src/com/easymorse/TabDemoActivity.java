package com.easymorse;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TabHost;

public class TabDemoActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.setTitle("演示标签分页");
        
        TabHost tabHost=(TabHost) this.findViewById(R.id.tabhost);
        tabHost.setup();
        
        TabHost.TabSpec spec=tabHost.newTabSpec("clockTab");
        spec.setContent(R.id.AnalogClock01);
        spec.setIndicator("模拟时钟");
        tabHost.addTab(spec);
        
        spec=tabHost.newTabSpec("buttonTab");
        spec.setContent(R.id.DigitalClock01);
        spec.setIndicator("数字时钟");
        tabHost.addTab(spec);
        
        tabHost.setCurrentTab(0);
        
    }
}