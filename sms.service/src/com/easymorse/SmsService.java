package com.easymorse;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class SmsService extends Service {

	private boolean started;

	private boolean threadDisable;

	private ServiceBinder serviceBinder = new ServiceBinder();

	public class ServiceBinder extends Binder implements ISmsService {

		@Override
		public boolean isStarted() {
			return started;
		}

		@Override
		public void start() {
			started=true;
			Log.d("sms.service", "sms service started.");
		}

		@Override
		public void stop() {
			started=false;
			Log.d("sms.service", "sms service stopped.");
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return serviceBinder;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		Thread thread = new Thread() {
			@Override
			public void run() {
				while (!threadDisable) {
					try {
						if (started) {
							Log.d("sms.service", "send a sms message.");
						}
						Thread.sleep(1000 * 5);
					} catch (InterruptedException e) {
					}
				}
			}
		};

		thread.start();

		Log.d("sms.service", "sms service created.");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		threadDisable = true;
		Log.d("sms.service", "sms service shutdown.");
	}
}
