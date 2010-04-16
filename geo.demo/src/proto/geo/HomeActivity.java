package proto.geo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeActivity extends Activity {

	Button button;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		button = (Button) this.findViewById(R.id.Button01);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final CharSequence[] items = { "乘车", "步行", "驾车" };
				AlertDialog.Builder builder = new AlertDialog.Builder(
						HomeActivity.this);
				builder.setTitle("选择交通方式");
				builder.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						StringBuilder params = new StringBuilder().append("&dirflg=");
						switch (item) {
						case 0:
							params.append("r");
							break;
						case 1:
							params.append("w");
							break;
						case 2:
							params.append("d");
							break;
						default:
							break;
						}
						getMap(params.toString());
					}
				});

				AlertDialog alert = builder.create();
				alert.show();
			}
		});
	}

	protected void getMap(String params) {
		Intent i = new Intent(
				Intent.ACTION_VIEW,
				Uri
						.parse("http://ditu.google.cn/maps?f=d&source=s_d&saddr=31.249351,121.45905&daddr=31.186371,121.489885&hl=zh&t=m&"
								+ params));
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				& Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
		i.setClassName("com.google.android.apps.maps",
				"com.google.android.maps.MapsActivity");
		startActivity(i);
	}
}