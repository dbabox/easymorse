package com.easymorse;

import android.app.TabActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class SmsServiceOptions extends TabActivity {

	private boolean isStart;

	private SharedPreferences preferences;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		this.setTitle("短信服务器");

		TabHost tabHost = this.getTabHost();
		tabHost.setup();

		TabHost.TabSpec spec = tabHost.newTabSpec("服务选项");
		spec.setContent(R.id.Option01);
		spec.setIndicator("服务选项");
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec("服务状态");
		spec.setContent(R.id.Option02);
		spec.setIndicator("服务状态");
		tabHost.addTab(spec);

		preferences = this.getSharedPreferences("sms.service.easymorse.com", 0);
		isStart = preferences.getBoolean("sms.service.is.start", false);

		RadioGroup radioGroup = (RadioGroup) this
				.findViewById(R.id.radioGroup01);
		if (isStart) {
			radioGroup.check(R.id.radioButtonStart);
		} else {
			radioGroup.check(R.id.radioButtonStop);
		}

		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.radioButtonStart) {
					isStart = true;
					Log.v("sms.service", ">>start service...");
				} else {
					isStart = false;
					Log.v("sms.service", ">>stop service...");
				}
				preferences.edit().putBoolean("sms.service.is.start", isStart)
						.commit();
			}
		});
	}
}