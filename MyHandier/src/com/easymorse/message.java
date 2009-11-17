package com.easymorse;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
public class message extends Activity {
	public int intCounter = 0;
	public RadioGroup radioGroup ;
	/* 自定义Handler信息代码，用以作为识别事件处理 */
	protected static final int GUI_STOP_NOTIFIER = 0x108;
	protected static final int GUI_THREADING_NOTIFIER = 0x109;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		 radioGroup = (RadioGroup) this
				.findViewById(R.id.radioGroup01);
		TextView textView = (TextView) findViewById(R.id.text);
		RadioButton radioButton = (RadioButton) findViewById(R.id.radioButtonStart);
		// 默认开启
		radioGroup.check(R.id.radioButtonStart);
		new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 10; i++) {
					try {
						intCounter = i++;
						Thread.sleep(1000);
						if (i == 9) {
							Message m = new Message();
							m.what = message.GUI_STOP_NOTIFIER;
							message.this.myMessageHandler.sendMessage(m);
							break;
						} 
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	Handler myMessageHandler = new Handler() {
		// @Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case message.GUI_STOP_NOTIFIER:
				radioGroup.check(R.id.radioButtonStop);
			}
			super.handleMessage(msg);
		}
	};

}