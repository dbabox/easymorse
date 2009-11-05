package com.easymorse;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class SmsService extends Service {

	private boolean started;

	private boolean threadDisable;

	private ISmsService.Stub serviceBinder = new ISmsService.Stub() {

		@Override
		public void stop() throws RemoteException {
			started = false;
			Log.d("sms.service", "sms service stopped.");
		}

		@Override
		public void start() throws RemoteException {
			started = true;
			Log.d("sms.service", "sms service started.");
		}

		@Override
		public boolean isStarted() throws RemoteException {
			return started;
		}
	};

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
