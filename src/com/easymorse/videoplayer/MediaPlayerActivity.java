package com.easymorse.videoplayer;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

public class MediaPlayerActivity extends Activity {
	private VideoView videoView;

	private ImageView imageView;
	
	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.video);

		this.videoView = (VideoView) this.findViewById(R.id.videoView);
		this.imageView = (ImageView) this.findViewById(R.id.image);
		this.imageView.setImageResource(R.drawable.ad);
		
		this.progressBar=(ProgressBar) this.findViewById(R.id.progress);

		MediaController controller = new MediaController(this);
		this.videoView.setMediaController(controller);
		this.videoView.setOnPreparedListener(new OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				progressBar.setVisibility(View.GONE);
				imageView.setVisibility(View.GONE);
				mp.start();
			}
		});

		videoView
				.setVideoURI(Uri
						.parse("http://wangjun.easymorse.com/wp-content/video/mp4/tuzi.3gp"));
	}
}
