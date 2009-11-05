package com.easymorse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SmsServiceBootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent myIntent = new Intent();
		myIntent.setAction("com.easymorse.SmsService");
		context.startService(myIntent);
	}

}
