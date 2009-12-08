package com.easymorse;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class Mp4PlayerActivity extends Activity {

	VideoView videoView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		videoView = (VideoView) this.findViewById(R.id.videoView);
		MediaController controller = new MediaController(this);
		this.videoView.setMediaController(controller);
		
		videoView.setVideoURI(Uri.parse("http://sayedhashimi.com/downloads/android/movie.mp4"));
		videoView.requestFocus();
	}
}