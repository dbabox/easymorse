package com.easymorse;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
public class androidmp4demo extends Activity {
	private String path = "http://dev.mopietek.net:8080/mp4/avi512.mp4";
	private  VideoView mVideoView;
	private static int i = 0;
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.videoview);
		mVideoView = (VideoView) findViewById(R.id.surface_view);
		if (path == "") {
			Toast.makeText(
					androidmp4demo.this,
					"Please edit VideoViewDemo Activity, and set path"
							+ " variable to your media file URL/path",
					Toast.LENGTH_LONG).show();
		} else {
			mVideoView.setVideoPath(path);
			MediaController controller = new MediaController(this);
			mVideoView.setMediaController(controller);
			mVideoView.requestFocus();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.i("mp4", "@@@ on start");
	}

	@Override
	protected void onResume() {
		super.onResume();
		mVideoView.seekTo(i);
		mVideoView.start();
		Log.i("mp4", "@@@ on resume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.i("mp4", "@@@ on pause");
	}
	@Override
	protected void onStop() {
		super.onStop();
			mVideoView.pause();
			i = mVideoView.getCurrentPosition();
		Log.i("mp4", "@@@ on stop");
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mVideoView.destroyDrawingCache();
		Log.i("mp4", "@@@ on destroy");
	}
}
