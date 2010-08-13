package com.easymorse.lazyload;

import java.net.URL;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

public class LazyLoadImageActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		loadImage(R.id.image1, "http://www.google.com.hk/intl/zh-CN/images/logo_cn.png");
		loadImage(R.id.image2, "http://www.baidu.com/img/baidu_logo.gif");
		loadImage(R.id.image3, "http://cache.soso.com/30d/img/web/logo.gif");
	}

	private void loadImage(int id, String url) {
		ImageView imageView = (ImageView) this.findViewById(id);
		
		try {
			imageView.setImageDrawable(Drawable.createFromStream(new URL(url)
					.openStream(), "logo.png"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}