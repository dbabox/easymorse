package com.easymorse;

import android.app.TabActivity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class SmsServiceOptions extends TabActivity {

	private RadioGroup radioGroup;

	private ISmsService smsService;

	private ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			smsService = (ISmsService) service;

			try {
				if (smsService.isStarted()) {
					radioGroup.check(R.id.radioButtonStart);
				} else {
					radioGroup.check(R.id.radioButtonStop);
				}
			} catch (RemoteException e) {
				Log.e("sms.service", "error: " + e.getMessage(), e);
				throw new RuntimeException(e);
			}

			radioGroup
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(RadioGroup group,
								int checkedId) {
							if (checkedId == R.id.radioButtonStart) {
								Log.d("sms.service", "starting service...");
								try {
									smsService.start();
								} catch (RemoteException e) {
									Log.e("sms.service", "error: "
											+ e.getMessage(), e);
									throw new RuntimeException(e);
								}
							} else {
								Log.d("sms.service", "stopping service...");
								try {
									smsService.stop();
								} catch (RemoteException e) {
									Log.e("sms.service", "error: "
											+ e.getMessage(), e);
									throw new RuntimeException(e);
								}
							}
						}
					});
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			smsService = null;
		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		this.setTitle("短信服务器");
		this.bindService(new Intent("com.easymorse.SmsService"),
				this.serviceConnection, BIND_AUTO_CREATE);

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

		radioGroup = (RadioGroup) this.findViewById(R.id.radioGroup01);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.unbindService(serviceConnection);
	}
}