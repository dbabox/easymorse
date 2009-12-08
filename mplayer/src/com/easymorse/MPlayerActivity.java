package com.easymorse;

import java.io.IOException;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MPlayerActivity extends Activity implements
		OnBufferingUpdateListener, OnCompletionListener,
		MediaPlayer.OnPreparedListener, SurfaceHolder.Callback {

	private MediaPlayer mediaPlayer;

	private SurfaceView surfaceView;

	private SurfaceHolder surfaceHolder;

	private int videoWidth;

	private int videoHeight;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		this.surfaceView = (SurfaceView) this.findViewById(R.id.surface);
		this.surfaceHolder = this.surfaceView.getHolder();
		this.surfaceHolder.addCallback(this);
		this.surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		Log.v("mplayer", ">>>create ok.");
	}

	private void playVideo() throws IllegalArgumentException,
			IllegalStateException, IOException {
		this.mediaPlayer = new MediaPlayer();
		this.mediaPlayer
				.setDataSource("http://sayedhashimi.com/downloads/android/movie.mp4");
		this.mediaPlayer.setDisplay(this.surfaceHolder);
		this.mediaPlayer.prepare();
		this.mediaPlayer.setOnBufferingUpdateListener(this);
		this.mediaPlayer.setOnPreparedListener(this);
		this.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		Log.v("mplayer", ">>>play video");
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		this.videoWidth = this.mediaPlayer.getVideoWidth();
		this.videoHeight = this.mediaPlayer.getVideoHeight();

		if (this.videoHeight != 0 && this.videoWidth != 0) {
			this.surfaceHolder.setFixedSize(this.videoWidth, this.videoHeight);
			this.mediaPlayer.start();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Log.v("mplayer", ">>>surface changed");
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			this.playVideo();
		} catch (Exception e) {
			Log.e("mplayer", ">>>error", e);
		}
		Log.v("mplayer", ">>>surface created");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.v("mplayer", ">>>surface destroyed");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (this.mediaPlayer != null) {
			this.mediaPlayer.release();
			this.mediaPlayer = null;
		}
	}
}