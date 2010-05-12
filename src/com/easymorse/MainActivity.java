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

	private String[][] subTitles = { { "动力相关信息", "getEngineInfo" },{"控制相关","getControlInfo"},
			{ "车速和油耗", "getSpeedInfo" } ,{"外形和质量","getShapeInfo"}};

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
		car.getEngineInfo().add("最大输出扭矩 155牛.米/3500RPM");
		car.getEngineInfo().add("排放 中国四号标准，带OBD");
		car.setControlInfo(new ArrayList<String>());
		car.getControlInfo().add("变速器，5档手动变速器");
		car.getControlInfo().add("悬架系统，麦弗逊式独立悬架/四连杆式独立悬架");
		car.getControlInfo().add("制动系统，大尺寸前通风制动盘/后实心制动盘");
		car.getControlInfo().add("转向系统，EPS电动随速助力转向器");
		car.setSpeedInfo(new ArrayList<String>());
		car.getSpeedInfo().add("最高时速，185KM/H");
		car.getSpeedInfo().add("0-100KM/H时间，11.9S");
		car.getSpeedInfo().add("90KM/H等速油耗，5.6L/100KM");
		car.getSpeedInfo().add("综合油耗，6.9L/100KM");
		car.setShapeInfo(new ArrayList<String>());
		car.getShapeInfo().add("长x宽x高，4199x1786x1479");
		car.getShapeInfo().add("轴距，2578mm");
		car.getShapeInfo().add("前后轮距，1540mm/1513mm");
		car.getShapeInfo().add("整车质量，1275KG");
		car.getShapeInfo().add("行李箱容积，350L/1513L");
		car.getShapeInfo().add("油箱容积，55L");
		cars.add(car);

		car = new Car();
		car.setType("高尔夫 6 1.6L \n 自动时尚型");
		car.setEngineInfo(new ArrayList<String>());
		car.getEngineInfo().add("1.6L 直列四缸4气阀电喷燃油喷射");
		car.getEngineInfo().add("最大输出功率 77KW/5600RPM");
		car.getEngineInfo().add("最大输出扭矩 155牛.米/3500RPM");
		car.getEngineInfo().add("排放 中国四号标准，带OBD");
		car.setControlInfo(new ArrayList<String>());
		car.getControlInfo().add("变速器，带运动模式的7档位/自动一体DSG双离合自动变速器");
		car.getControlInfo().add("悬架系统，麦弗逊式独立悬架/四连杆式独立悬架");
		car.getControlInfo().add("制动系统，大尺寸前通风制动盘/后实心制动盘");
		car.getControlInfo().add("转向系统，EPS电动随速助力转向器");
		car.setSpeedInfo(new ArrayList<String>());
		car.getSpeedInfo().add("最高时速，180KM/H");
		car.getSpeedInfo().add("0-100KM/H时间，11.8S");
		car.getSpeedInfo().add("90KM/H等速油耗，5.9L/100KM");
		car.getSpeedInfo().add("综合油耗，6.6L/100KM");
		car.setShapeInfo(new ArrayList<String>());
		car.getShapeInfo().add("长x宽x高，4199x1786x1479");
		car.getShapeInfo().add("轴距，2578mm");
		car.getShapeInfo().add("前后轮距，1540mm/1513mm");
		car.getShapeInfo().add("整车质量，1295KG");
		car.getShapeInfo().add("行李箱容积，350L/1513L");
		car.getShapeInfo().add("油箱容积，55L");
		cars.add(car);
		
		car = new Car();
		car.setType("高尔夫 6 1.6L \n 手动舒适型");
		car.setEngineInfo(new ArrayList<String>());
		car.getEngineInfo().add("1.6L 直列四缸4气阀电喷燃油喷射");
		car.getEngineInfo().add("最大输出功率 77KW/5600RPM");
		car.getEngineInfo().add("最大输出扭矩 155牛.米/3500RPM");
		car.getEngineInfo().add("排放 中国四号标准，带OBD");
		car.setSpeedInfo(new ArrayList<String>());
		car.setControlInfo(new ArrayList<String>());
		car.getControlInfo().add("变速器，5档手动变速器");
		car.getControlInfo().add("悬架系统，麦弗逊式独立悬架/四连杆式独立悬架");
		car.getControlInfo().add("制动系统，大尺寸前通风制动盘/后实心制动盘");
		car.getControlInfo().add("转向系统，EPS电动随速助力转向器");
		car.getSpeedInfo().add("最高时速，185KM/H");
		car.getSpeedInfo().add("0-100KM/H时间，11.9S");
		car.getSpeedInfo().add("90KM/H等速油耗，5.6L/100KM");
		car.getSpeedInfo().add("综合油耗，6.9L/100KM");
		car.setShapeInfo(new ArrayList<String>());
		car.getShapeInfo().add("长x宽x高，4199x1786x1479");
		car.getShapeInfo().add("轴距，2578mm");
		car.getShapeInfo().add("前后轮距，1540mm/1513mm");
		car.getShapeInfo().add("整车质量，1275KG");
		car.getShapeInfo().add("行李箱容积，350L/1513L");
		car.getShapeInfo().add("油箱容积，55L");
		cars.add(car);
		
		car = new Car();
		car.setType("高尔夫 6 1.6L \n 自动舒适型");
		car.setEngineInfo(new ArrayList<String>());
		car.getEngineInfo().add("1.6L 直列四缸4气阀电喷燃油喷射");
		car.getEngineInfo().add("最大输出功率 77KW/5600RPM");
		car.getEngineInfo().add("最大输出扭矩 155牛.米/3500RPM");
		car.getEngineInfo().add("排放 中国四号标准，带OBD");
		car.setControlInfo(new ArrayList<String>());
		car.getControlInfo().add("变速器，带运动模式的7档位/自动一体DSG双离合自动变速器");
		car.getControlInfo().add("悬架系统，麦弗逊式独立悬架/四连杆式独立悬架");
		car.getControlInfo().add("制动系统，大尺寸前通风制动盘/后实心制动盘");
		car.getControlInfo().add("转向系统，EPS电动随速助力转向器");
		car.setSpeedInfo(new ArrayList<String>());
		car.getSpeedInfo().add("最高时速，180KM/H");
		car.getSpeedInfo().add("0-100KM/H时间，11.8S");
		car.getSpeedInfo().add("90KM/H等速油耗，5.9L/100KM");
		car.getSpeedInfo().add("综合油耗，6.6L/100KM");
		car.setShapeInfo(new ArrayList<String>());
		car.getShapeInfo().add("长x宽x高，4199x1786x1479");
		car.getShapeInfo().add("轴距，2578mm");
		car.getShapeInfo().add("前后轮距，1540mm/1513mm");
		car.getShapeInfo().add("整车质量，1295KG");
		car.getShapeInfo().add("行李箱容积，350L/1513L");
		car.getShapeInfo().add("油箱容积，55L");
		cars.add(car);
		
		car = new Car();
		car.setType("高尔夫 6 1.4TSI \n 手动舒适型");
		car.setEngineInfo(new ArrayList<String>());
		car.getEngineInfo().add("1.4L 直列四缸4气阀 TSI电控油缸内直接喷射涡轮增压");
		car.getEngineInfo().add("最大输出功率 96KW/5000RPM");
		car.getEngineInfo().add("最大输出扭矩 220牛.米/1750-3500RPM");
		car.getEngineInfo().add("排放 中国四号标准，带OBD");
		car.setControlInfo(new ArrayList<String>());
		car.getControlInfo().add("变速器，5档手动变速器");
		car.getControlInfo().add("悬架系统，麦弗逊式独立悬架/四连杆式独立悬架");
		car.getControlInfo().add("制动系统，大尺寸前通风制动盘/后实心制动盘");
		car.getControlInfo().add("转向系统，EPS电动随速助力转向器");
		car.setSpeedInfo(new ArrayList<String>());
		car.getSpeedInfo().add("最高时速，200KM/H");
		car.getSpeedInfo().add("0-100KM/H时间，9.6S");
		car.getSpeedInfo().add("90KM/H等速油耗，5.5L/100KM");
		car.getSpeedInfo().add("综合油耗，6.3L/100KM");
		car.setShapeInfo(new ArrayList<String>());
		car.getShapeInfo().add("长x宽x高，4199x1786x1479");
		car.getShapeInfo().add("轴距，2578mm");
		car.getShapeInfo().add("前后轮距，1540mm/1513mm");
		car.getShapeInfo().add("整车质量，1295KG");
		car.getShapeInfo().add("行李箱容积，350L/1513L");
		car.getShapeInfo().add("油箱容积，55L");
		cars.add(car);
		
		car = new Car();
		car.setType("高尔夫 6 1.4TSI \n 自动舒适型");
		car.setEngineInfo(new ArrayList<String>());
		car.getEngineInfo().add("1.4L 直列四缸4气阀 TSI电控油缸内直接喷射涡轮增压");
		car.getEngineInfo().add("最大输出功率 96KW/5000RPM");
		car.getEngineInfo().add("最大输出扭矩 220牛.米/1750-3500RPM");
		car.getEngineInfo().add("排放 中国四号标准，带OBD");
		car.setControlInfo(new ArrayList<String>());
		car.getControlInfo().add("变速器，带运动模式的7档位/自动一体DSG双离合自动变速器");
		car.getControlInfo().add("悬架系统，麦弗逊式独立悬架/四连杆式独立悬架");
		car.getControlInfo().add("制动系统，大尺寸前通风制动盘/后实心制动盘");
		car.getControlInfo().add("转向系统，EPS电动随速助力转向器");
		car.setSpeedInfo(new ArrayList<String>());
		car.getSpeedInfo().add("最高时速，200KM/H");
		car.getSpeedInfo().add("0-100KM/H时间，9.5S");
		car.getSpeedInfo().add("90KM/H等速油耗，5.8L/100KM");
		car.getSpeedInfo().add("综合油耗，6.0L/100KM");
		car.setShapeInfo(new ArrayList<String>());
		car.getShapeInfo().add("长x宽x高，4199x1786x1479");
		car.getShapeInfo().add("轴距，2578mm");
		car.getShapeInfo().add("前后轮距，1540mm/1513mm");
		car.getShapeInfo().add("整车质量，1295KG");
		car.getShapeInfo().add("行李箱容积，350L/1513L");
		car.getShapeInfo().add("油箱容积，55L");
		cars.add(car);
		
		car = new Car();
		car.setType("高尔夫 6 1.4TSI \n 自动豪华型");
		car.setEngineInfo(new ArrayList<String>());
		car.getEngineInfo().add("1.4L 直列四缸4气阀 TSI电控油缸内直接喷射涡轮增压");
		car.getEngineInfo().add("最大输出功率 96KW/5000RPM");
		car.getEngineInfo().add("最大输出扭矩 220牛.米/1750-3500RPM");
		car.getEngineInfo().add("排放 中国四号标准，带OBD");
		car.setControlInfo(new ArrayList<String>());
		car.getControlInfo().add("变速器，带运动模式的7档位/自动一体DSG双离合自动变速器");
		car.getControlInfo().add("悬架系统，麦弗逊式独立悬架/四连杆式独立悬架");
		car.getControlInfo().add("制动系统，大尺寸前通风制动盘/后实心制动盘");
		car.getControlInfo().add("转向系统，EPS电动随速助力转向器");
		car.setSpeedInfo(new ArrayList<String>());
		car.getSpeedInfo().add("最高时速，200KM/H");
		car.getSpeedInfo().add("0-100KM/H时间，9.5S");
		car.getSpeedInfo().add("90KM/H等速油耗，5.8L/100KM");
		car.getSpeedInfo().add("综合油耗，6.0L/100KM");
		car.setShapeInfo(new ArrayList<String>());
		car.getShapeInfo().add("长x宽x高，4199x1786x1479");
		car.getShapeInfo().add("轴距，2578mm");
		car.getShapeInfo().add("前后轮距，1540mm/1513mm");
		car.getShapeInfo().add("整车质量，1295KG");
		car.getShapeInfo().add("行李箱容积，350L/1513L");
		car.getShapeInfo().add("油箱容积，55L");
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
