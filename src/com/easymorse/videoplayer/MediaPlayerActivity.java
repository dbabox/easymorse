package com.easymorse.videoplayer;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MediaPlayerActivity extends Activity {
	private VideoView videoView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.video);
		
		this.videoView=(VideoView) this.findViewById(R.id.videoView);
		MediaController controller=new MediaController(this);
		this.videoView.setMediaController(controller);
		
		videoView.setVideoURI(Uri.parse("http://wangjun.easymorse.com/wp-content/video/mp4/tuzi.3gp"));
		videoView.requestFocus();
	}
}
