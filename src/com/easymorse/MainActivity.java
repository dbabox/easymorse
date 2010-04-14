package com.easymorse;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.OnGestureListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class MainActivity extends Activity implements OnGestureListener {

	static final int FLING_MIN_DISTANCE = 120, FLING_MIN_VELOCITY = 200;

	private String[][] subTitles = { { "动力相关信息", "getEngineInfo" },
			{ "车速和油耗", "getSpeedInfo" } };

	private ViewFlipper flipper;

	private GestureDetector detector;

	private List<Car> cars;

	{
		cars = new ArrayList<Car>();

		Car car = new Car();
		car.setType("高尔夫 6 1.6L \n 手动时尚型");
		car.setEngineInfo(new ArrayList<String>());
		car.getEngineInfo().add("1.6L 直列四缸4气阀电喷燃油喷射");
		car.getEngineInfo().add("最大输出功率 77KW/5600RPM");
		car.setSpeedInfo(new ArrayList<String>());
		car.getSpeedInfo().add("最高时速，185KM/H");
		car.getSpeedInfo().add("0-100KM/H时间，11.9S");
		cars.add(car);

		car = new Car();
		car.setType("高尔夫 6 1.6L \n 自动时尚型");
		car.setEngineInfo(new ArrayList<String>());
		car.getEngineInfo().add("1.6L 直列四缸4气阀电喷燃油喷射");
		car.getEngineInfo().add("最大输出功率 77KW/5600RPM");
		car.setSpeedInfo(new ArrayList<String>());
		car.getSpeedInfo().add("最高时速，180KM/H");
		car.getSpeedInfo().add("0-100KM/H时间，11.8S");
		cars.add(car);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		detector = new GestureDetector(this);
		flipper = (ViewFlipper) this.findViewById(R.id.ViewFlipper01);

		for (Car car : cars) {
			LinearLayout frame = (LinearLayout) this.getLayoutInflater()
					.inflate(R.layout.frame, null);
			TextView textView = (TextView) frame.findViewById(R.id.TextView01);
			textView.setText(car.getType());

			ViewFlipper flipper2 = (ViewFlipper) this.getLayoutInflater().inflate(R.layout.flipper2, null);
			flipper2.setTag("flipper2");

			for (String[] s : this.subTitles) {
				LinearLayout layout = (LinearLayout) this.getLayoutInflater()
						.inflate(R.layout.content, null);
				TextView view = (TextView) layout
						.findViewById(R.id.SubtitleLabel);
				view.setText(s[0]);
				flipper2.addView(layout);

				List<String> items = null;

				try {
					items = (List<String>) car.getClass().getMethod(s[1], null)
							.invoke(car, null);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}

				for (String i : items) {
					LinearLayout layout2 = (LinearLayout) this
							.getLayoutInflater().inflate(R.layout.item, null);
					layout.addView(layout2);
					TextView textView2 = (TextView) layout2
							.findViewById(R.id.ContentItemText);
					textView2.setText(i);
				}
			}

			frame.addView(flipper2);
			flipper.addView(frame);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return this.detector.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		ViewFlipper flipper2 = (ViewFlipper) this.flipper.getCurrentView()
				.findViewWithTag("flipper2");
		if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE) {
			this.flipper.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.push_left_in));
			this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.push_left_out));
			this.flipper.showNext();
			return true;
		} else if (e1.getX() - e2.getX() < -FLING_MIN_DISTANCE) {
			this.flipper.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.push_right_in));
			this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.push_right_out));
			this.flipper.showPrevious();
			return true;
		} else if (e1.getY() - e2.getY() > FLING_MIN_DISTANCE) {
			flipper2.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.push_up_in));
			flipper2.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.push_up_out));
			flipper2.showNext();
			return true;
		} else if (e1.getY() - e2.getY() < -FLING_MIN_DISTANCE) {
			flipper2.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.push_down_in));
			flipper2.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.push_down_out));
			flipper2.showPrevious();
			return true;
		}
		return false;

	}

	@Override
	public void onLongPress(MotionEvent e) {
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}
}
