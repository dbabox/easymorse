package com.easymorse;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
public class PlayMp4 extends Activity {
	private String path = "";
	private VideoView mVideoView;
	private static int i = 0;
	private int width;
	private int heigh;	
	private Dialog dialog;
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
//		  LinearLayout layout = new LinearLayout(this);
//		  layout.setBackgroundColor(Color.RED);
		//去掉头信息
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		 getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
	                WindowManager.LayoutParams.FLAG_FULLSCREEN);  
		//setWallpaper(bitmap)
		 
			Bundle bundle = this.getIntent().getExtras();
			
			//判断手机屏幕的方向
			DisplayMetrics dm = new DisplayMetrics(); 
	        getWindowManager().getDefaultDisplay().getMetrics(dm);
			width=dm.widthPixels;
			heigh=dm.heightPixels;
			if(width/heigh>0)
			{
				setContentView(R.layout.videoview);
				//横屏
				path = bundle.getString("widthurl");
				Log.i("mp4", "heng"+path);
			}
			if(width/heigh==0)
			{
				setContentView(R.layout.view);
				//竖屏
				path = bundle.getString("heighturl");
				Log.i("mp4", "shu"+path);
			}
		 
        //创建进度条
		 dialog=ProgressDialog.show(this, "正在加载...", "三枪马上开始");
		 
		mVideoView = (VideoView) findViewById(R.id.surface_view);
		
		
		mVideoView.setBackgroundDrawable(getWallpaper());
		
		mVideoView.setVideoPath(path);
		MediaController controller = new MediaController(this);
		mVideoView.setMediaController(controller);
		mVideoView.requestFocus();

		mVideoView.setOnPreparedListener(new OnPreparedListener() {
			// 开始播放
			@Override
			public void onPrepared(MediaPlayer mp) {
				mVideoView.setBackgroundColor(Color.argb(0, 0, 255, 0));
				dialog.dismiss();
			}
		});
		//播放完毕
		mVideoView.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				Toast.makeText(PlayMp4.this, "播放完毕，谢谢观看。。。。。", Toast.LENGTH_LONG)
						.show();
			}
		});
		
	}
	@Override
	protected void onResume() {
		super.onResume();
		mVideoView.seekTo(i);
		mVideoView.start();
	}
	@Override
	protected void onStop() {
		super.onStop();
		mVideoView.pause();
		i = mVideoView.getCurrentPosition();
	}
}
