package com.easymorse.lazyload;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class LazyLoadImageActivity extends Activity {

	private ExecutorService executorService = Executors.newFixedThreadPool(1);

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		loadImage("http://www.chinatelecom.com.cn/images/logo_new.gif", R.id.image1);
		loadImage("http://www.baidu.com/img/baidu_logo.gif", R.id.image2);
		loadImage("http://cache.soso.com/30d/img/web/logo.gif", R.id.image3);
	}

	@Override
	protected void onDestroy() {
		executorService.shutdown();
		super.onDestroy();
	}

	private void loadImage(final String url, final int id) {
		final Handler handler=new Handler();
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					final Drawable drawable = Drawable.createFromStream(
							new URL(url).openStream(), "image.png");
					handler.post(new Runnable() {

						@Override
						public void run() {
							((ImageView) LazyLoadImageActivity.this
									.findViewById(id))
									.setImageDrawable(drawable);
						}
					});
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
	}
}