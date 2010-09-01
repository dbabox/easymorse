package com.easymorse.videoplayer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.MediaController;
import android.widget.MediaController.MediaPlayerControl;

public class CustomerVideoView extends SurfaceView implements
		MediaPlayerControl {

	private static String TAG = "customer.videoplayer";

	private boolean pause;

	private boolean seekBackward;

	private boolean seekForward;

	private Uri videoUri;

	private MediaPlayer mediaPlayer;

	private Context context;

	private OnPreparedListener onPreparedListener;

	private MediaController mediaController;

	protected SurfaceHolder surfaceHolder;

	private Callback surfaceHolderCallback = new SurfaceHolder.Callback() {

		public void surfaceChanged(SurfaceHolder holder, int format, int w,
				int h) {
		}

		public void surfaceCreated(SurfaceHolder holder) {
			surfaceHolder = holder;
			if (mediaPlayer != null) {
				mediaPlayer.setDisplay(surfaceHolder);
				resume();
			} else {
				openVideo();
			}
		}

		public void surfaceDestroyed(SurfaceHolder holder) {
			surfaceHolder = null;
			if (mediaController != null) {
				mediaController.hide();
			}
			release();
		}
	};

	private void release() {
		if (mediaPlayer != null) {
			mediaPlayer.reset();
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	public void resume() {
		if (surfaceHolder == null) {
			return;
		}
		if (mediaPlayer != null) {
			return;
		}
		openVideo();
	}

	public CustomerVideoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		this.initVideoView();
	}

	public CustomerVideoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		this.initVideoView();
	}

	public CustomerVideoView(Context context) {
		super(context);
		this.context = context;
		this.initVideoView();
	}

	@Override
	public boolean canPause() {
		return this.pause;
	}

	@Override
	public boolean canSeekBackward() {
		return this.seekBackward;
	}

	@Override
	public boolean canSeekForward() {
		return this.seekForward;
	}

	@Override
	public int getBufferPercentage() {
		return 0;
	}

	@Override
	public int getCurrentPosition() {
		return mediaPlayer!=null?mediaPlayer.getCurrentPosition():0;
	}

	@Override
	public int getDuration() {
		return mediaPlayer!=null?mediaPlayer.getDuration():0;
	}

	@Override
	public boolean isPlaying() {
		return false;
	}

	@Override
	public void pause() {
	}

	@Override
	public void seekTo(int mSec) {
	}

	@Override
	public void start() {
	}

	public void setVideoURI(Uri uri) {
		this.videoUri = uri;
		openVideo();
		requestLayout();
		invalidate();
	}

	private void openVideo() {
		this.mediaPlayer = new MediaPlayer();
		try {
			this.mediaPlayer.setDataSource(this.context, this.videoUri);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			throw new RuntimeException(e);
		}
		this.mediaPlayer.prepareAsync();
		this.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		this.mediaPlayer.setOnPreparedListener(onPreparedListener);
		attachMediaController();

	}

	private void attachMediaController() {
		if (mediaPlayer != null && mediaController != null) {
			mediaController.setMediaPlayer(this);
			View anchorView = this.getParent() instanceof View ? (View) this
					.getParent() : this;
			mediaController.setAnchorView(anchorView);
			mediaController.setEnabled(true);
		}

	}

	public void setMediaController(MediaController controller) {
		if (mediaController != null) {
			mediaController.hide();
		}
		mediaController = controller;
		attachMediaController();
	}

	public void setOnPreparedListener(OnPreparedListener onPreparedListener) {
		this.onPreparedListener = onPreparedListener;
	}

	private void initVideoView() {
		getHolder().addCallback(surfaceHolderCallback);
		getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		setFocusable(true);
		setFocusableInTouchMode(true);
		requestFocus();
	}

}
