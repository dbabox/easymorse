package com.easymorse.surface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView {

	public MySurfaceView(Context context) {
		super(context);
		this.getHolder().addCallback(new Callback() {

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
			}

			@Override
			public void surfaceCreated(final SurfaceHolder holder) {
				new Thread() {
					public void run() {
						Canvas canvas=holder.lockCanvas();
						Paint paint=new Paint();
						paint.setStyle(Paint.Style.STROKE);
						paint.setColor(Color.RED);
						
						canvas.drawRect(new Rect(40, 60, 280, 180), paint);
						holder.unlockCanvasAndPost(canvas);
					}
				}.start();
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
			}
		});
	}

}
