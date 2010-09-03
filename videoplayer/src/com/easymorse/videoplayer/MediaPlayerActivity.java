package com.easymorse.videoplayer;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MediaPlayerActivity extends Activity implements OnGestureListener {
	private CustomerVideoView videoView;

	private ImageView imageView;

	private TextView timeTextView;

	private TextView titleTextView;

	private TextView clockTextView;

	private ScheduledExecutorService scheduledExecutorService;

	private Handler handler;

	private SimpleDateFormat dateFormat;

	private View mediaControllerLayout;

	private SeekBar videoSeekBar;

	private ImageButton playButton;

	private GestureDetector gestureDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.video);
		this.gestureDetector = new GestureDetector(this);

		this.dateFormat = new SimpleDateFormat("HH:mm:ss");

		this.scheduledExecutorService = Executors.newScheduledThreadPool(2);
		this.handler = new Handler();

		this.videoView = (CustomerVideoView) this.findViewById(R.id.videoView);
		this.imageView = (ImageView) this.findViewById(R.id.image);
		this.imageView.setImageResource(R.drawable.ad);

		this.timeTextView = (TextView) this.findViewById(R.id.timeText);
		this.titleTextView = (TextView) this.findViewById(R.id.videoTitle);
		this.clockTextView = (TextView) this.findViewById(R.id.clockText);
		this.videoSeekBar = (SeekBar) this.findViewById(R.id.videoSeekBar);
		this.videoSeekBar
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						if (fromUser) {
							videoView.seekTo((int) (progress * 1.0
									/ seekBar.getMax() * videoView
									.getDuration()));
							seekBar.setProgress(progress);
						}
					}
				});

		this.playButton = (ImageButton) this.findViewById(R.id.playButton);
		this.playButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (videoView.isPlaying()) {
					videoView.pause();
				} else {
					videoView.start();
				}
			}
		});

		this.mediaControllerLayout = this
				.findViewById(R.id.mediaControllerLayout);

		this.videoView.setOnPreparedListener(new OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				imageView.setVisibility(View.GONE);
				timeTextView.setVisibility(View.VISIBLE);
				titleTextView.setText("ÖÆ×÷»¨Ðõ");

				mp.start();

				scheduledExecutorService.scheduleAtFixedRate(new Runnable() {

					@Override
					public void run() {
						handler.post(new Runnable() {
							@Override
							public void run() {
								if (videoView.isPlaying()) {
									float position = videoView
											.getCurrentPosition();
									int duration = videoView.getDuration();
									timeTextView
											.setText(getTimeFormatValue((int) position)
													+ " / "
													+ getTimeFormatValue(duration));
									videoSeekBar.setProgress((int) (position
											/ duration * videoSeekBar.getMax()));
								}
							}
						});

					}
				}, 1000, 1000, TimeUnit.MILLISECONDS);

				scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
					@Override
					public void run() {
						handler.post(new Runnable() {

							@Override
							public void run() {
								clockTextView.setText(dateFormat
										.format(new Date()));
							}
						});

					}
				}, 1000, 1000 * 10, TimeUnit.MILLISECONDS);
			}
		});

		videoView
				.setVideoURI(Uri
						.parse("http://wangjun.easymorse.com/wp-content/video/mp4/tuzi.mp4"));
	}

	private static String getTimeFormatValue(long time) {
		return MessageFormat.format(
				"{0,number,00}:{1,number,00}:{2,number,00}",
				time / 1000 / 60 / 60, time / 1000 / 60 % 60, time / 1000 % 60);
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		mediaControllerLayout.setVisibility(View.VISIBLE);
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				mediaControllerLayout.setVisibility(View.INVISIBLE);
			}
		}, 1000);
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		if (distanceX > 0) {
			this.videoSeekBar
					.setProgress(this.videoSeekBar.getProgress() - 1);
		} else {
			this.videoSeekBar
					.setProgress(this.videoSeekBar.getProgress() + 1);
		}
		videoView.seekTo((int) (this.videoSeekBar.getProgress() * 1.0
				/ videoSeekBar.getMax() * videoView.getDuration()));
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		Toast.makeText(this, "taped", Toast.LENGTH_SHORT).show();
		if (videoView.isPlaying()) {
			videoView.pause();
		} else {
			videoView.start();
		}
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return this.gestureDetector.onTouchEvent(event);
	}
}
