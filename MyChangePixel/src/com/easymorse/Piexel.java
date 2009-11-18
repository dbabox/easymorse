package com.easymorse;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.Toast;
@SuppressWarnings("deprecation")
public class Piexel extends Activity {
	
	private float mX, mY;
	// 定义 宽 长 x y
	private int intWidth, intHeight, intDefaultX, intDefaultY;
	// 获取屏幕的像素
	private int intScreenX, intScreenY;
	private ImageView imageView;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 显示图片
		imageView = (ImageView) findViewById(R.id.image);

		// 得到屏幕的对象
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		// 取得屏幕的像素
		intScreenX = dm.widthPixels;
		intScreenY = dm.heightPixels;
		/* 设置图片的宽高 */
		intWidth = 50;
		intHeight = 50;
		//初始话图片的位置
		RestoreButton();
		Log.e("pixel", "ping mu de xiang su +" + intScreenX + ";" + intScreenY);
	}

	/* 覆盖触控事件 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//取得手指触发屏幕的像素
		float x = event.getX();
		float y = event.getY();
		Log.i("pixel", "ni chu muo wo le==" + x + ";" + y);
		try {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				picMove(x, y);
				break;
			case MotionEvent.ACTION_MOVE:
				picMove(x, y);
				break;
			case MotionEvent.ACTION_UP:
				picMove(x, y);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public void RestoreButton() {
		intDefaultX = ((intScreenX - intWidth) / 2);
		intDefaultY = ((intScreenY - intHeight) / 2);
		//显示图片的位置
		imageView.setLayoutParams(new AbsoluteLayout.LayoutParams(intWidth,
				intHeight, intDefaultX, intDefaultY));
		MakeTextToast("用手托着图片跑",true);
	}
	private void picMove(float x, float y) {
		// 默认微调图片与指针的相对位置 
		mX = x - (intWidth / 2);
		mY = y - (intHeight / 2);
		if ((mX + intWidth) > intScreenX) {
			mX = intScreenX - intWidth;
		}
		else if (mX < 0) {
			mX = 0;
		}
		else if ((mY + intHeight) > intScreenY) {
			mY = intScreenY - intHeight;
		}
		else if (mY < 0) {
			mY = 0;
		}
		imageView.setLayoutParams(new AbsoluteLayout.LayoutParams(intWidth,
				intHeight, (int) mX, (int) mY));
	}
	  //显示信息
	  public void MakeTextToast(String str, boolean isLong)
	  {
	    if(isLong==true)
	    {
	      Toast.makeText(Piexel.this, str, Toast.LENGTH_LONG).show();
	    }
	    else
	    {
	      Toast.makeText(Piexel.this, str, Toast.LENGTH_SHORT).show();
	    }
	  }
}