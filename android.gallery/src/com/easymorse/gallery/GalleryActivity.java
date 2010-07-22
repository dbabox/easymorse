package com.easymorse.gallery;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GalleryActivity extends Activity {

	private static int SELECT_PICTURE;

	private File tempFile;

	Button button;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.tempFile=new File("/sdcard/a.jpg");
		
		button = new Button(this);
		button.setText("获取图片");
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				intent.putExtra("crop", "true");

				// intent.putExtra("aspectX", 1);
				// intent.putExtra("aspectY", 2);

				intent.putExtra("output", Uri.fromFile(tempFile));
				intent.putExtra("outputFormat", "JPEG");

				startActivityForResult(Intent.createChooser(intent, "选择图片"),
						SELECT_PICTURE);
			}
		});
		setContentView(button);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == SELECT_PICTURE) {
				// button.setText(tempFile.exists() + "");
				button.setBackgroundDrawable(Drawable.createFromPath(tempFile
						.getAbsolutePath()));
			}
		}
	}

}