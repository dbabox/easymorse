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
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

public class MediaPlayerActivity extends Activity {
	private CustomerVideoView videoView;

	private ImageView imageView;

	private TextView timeTextView;

	private TextView titleTextView;

	private TextView clockTextView;

	private ScheduledExecutorService scheduledExecutorService;

	private Handler handler;

	private SimpleDateFormat dateFormat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.video);

		this.dateFormat = new SimpleDateFormat("HH:mm:ss");

		this.scheduledExecutorService = Executors.newScheduledThreadPool(2);
		this.handler = new Handler();

		this.videoView = (CustomerVideoView) this.findViewById(R.id.videoView);
		this.imageView = (ImageView) this.findViewById(R.id.image);
		this.imageView.setImageResource(R.drawable.ad);

		this.timeTextView = (TextView) this.findViewById(R.id.timeText);
		this.titleTextView = (TextView) this.findViewById(R.id.videoTitle);
		this.clockTextView = (TextView) this.findViewById(R.id.clockText);

		MediaController controller = new MediaController(this);
		this.videoView.setMediaController(controller);
		this.videoView.setOnPreparedListener(new OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				// progressBar.setVisibility(View.GONE);
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
								int position = videoView.getCurrentPosition();
								int duration = videoView.getDuration();
								timeTextView
										.setText(getTimeFormatValue(position)
												+ " / "
												+ getTimeFormatValue(duration));
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
						.parse("http://wangjun.easymorse.com/wp-content/video/mp4/tuzi.3gp"));
	}

	private static String getTimeFormatValue(long time) {
		return MessageFormat.format(
				"{0,number,00}:{1,number,00}:{2,number,00}",
				time / 1000 / 60 / 60, time / 1000 / 60 % 60, time / 1000 % 60);
	}
}
