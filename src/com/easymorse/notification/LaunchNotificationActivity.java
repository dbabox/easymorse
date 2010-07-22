package com.easymorse.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

public class LaunchNotificationActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);

		TextView textView = new TextView(this);
		textView.setText("演示生成通知。");

		Button button = new Button(this);
		button.setText("收到公文");
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				Notification notification = new Notification(R.drawable.icon,
						"收到公文", System.currentTimeMillis());
				
				RemoteViews remoteViews=new RemoteViews(getPackageName(), R.layout.notification);
				remoteViews.setImageViewResource(R.id.image, R.drawable.user);
				remoteViews.setTextViewText(R.id.text, "张三-差旅费-上海世博会-3646元");
				notification.contentView=remoteViews;
				notification.contentIntent=PendingIntent.getActivity(
						LaunchNotificationActivity.this, 0,new Intent(LaunchNotificationActivity.this,LaunchNotificationActivity.class), 0);
				
				notification.flags|=Notification.FLAG_AUTO_CANCEL;
				notification.defaults |= Notification.DEFAULT_SOUND;
				manager.notify(1, notification);
			}
		});

		layout.addView(textView);
		layout.addView(button);

		this.setContentView(layout);
	}
}