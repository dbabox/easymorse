package com.easymorse;

import java.util.ArrayList;
import java.util.List;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;

public class CustomerPlayer extends TabActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		final TabHost tabHost = getTabHost();
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(null,
				getResources().getDrawable(R.drawable.menu)).setContent(
				new TabContentFactory() {
					List<String> items;
					{
						items = new ArrayList<String>();
						items.add("视频");
						items.add("听听");
						items.add("社区");
					}

					@Override
					public View createTabContent(String tag) {
						ListView listView = new ListView(CustomerPlayer.this);
						listView.setLayoutParams(new LinearLayout.LayoutParams(
								LinearLayout.LayoutParams.FILL_PARENT,
								LinearLayout.LayoutParams.WRAP_CONTENT));
						listView.setBackgroundResource(R.color.solid);
						listView.setCacheColorHint(Color.WHITE);
						listView.setAdapter(new ListAdapter(
								CustomerPlayer.this, 0, items));
						listView.setDivider(getResources().getDrawable(
								R.drawable.tabhost));
						listView.setDividerHeight(2);
						listView
								.setOnItemClickListener(new OnItemClickListener() {
									@Override
									public void onItemClick(
											AdapterView<?> parent, View view,
											int position, long id) {
										tabHost.setCurrentTab(1);
									}
								});
						return listView;
					}
				}));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(null,
				getResources().getDrawable(R.drawable.vod2)).setContent(
				new TabContentFactory() {
					List<Content> items;
					{
						items = new ArrayList<Content>();
						Content content = new Content();
						content.setTitle("阿童木");
						content
								.setComment("因意外痛失爱子的天才科学家天马博士悲痛欲绝，萌生了复制爱子的想法并成功创造了机器人阿童木，他和人类一样拥有喜怒哀乐，而且还有博士儿子残余的情感与记忆。但阿童木却无法令抑郁的博士重展欢颜，博士狠心决定将他遗弃...");
						content.setImageUrl("" + R.drawable.c1);
						content
								.setUrl("http://dev2.mopietek.net:8080/mp4/320480flv.3gp");
						content.setHeightUrl("/sdcard/Video/ztx0520.3gp");
						content.setWidthUrl("/sdcard/Video/ztx0520.3gp");
						items.add(content);
						content = new Content();
						content.setTitle("三枪拍案惊奇");
						content
								.setComment("故事发生于古代某大漠之中，一家面馆老板名为王五麻子，为人阴险吝啬，如此人品，老婆自然不待见。恰好伙计李四长得很帅又有幽默感，两人一拍即合，有了私情。王五发现两人私情后...");
						content.setImageUrl("" + R.drawable.c2);
						content
								.setUrl("http://dev2.mopietek.net:8080/mp4/320480flv.3gp");
						content
								.setHeightUrl("http://192.168.0.56:8080/mp4/flv.3gp");
						content
								.setWidthUrl("http://192.168.0.56:8080/mp4/ztx0520.3gp");
						items.add(content);

						content = new Content();
						content.setTitle("花木兰");
						content
								.setComment("在古老的中国，有一位个性爽朗，性情善良的好女孩，名字叫作「花木兰」，身为花家的大女儿，花木兰在父母开明的教悔下，一直很期待自己能花家带来荣耀。不过就在北方匈奴来犯...");
						content.setImageUrl("" + R.drawable.c3);
						content
								.setUrl("http://dev2.mopietek.net:8080/mp4/320480flv.3gp");
						content
								.setHeightUrl("http://192.168.0.56:8080/mp4/xyx.3gp");
						content
								.setWidthUrl("http://dev2.mopietek.net:8080/mp4/480320flv.3gp");
						items.add(content);

						content = new Content();
						content.setTitle("达芬奇密码");
						content
								.setComment("本片讲述了哈佛大学的符号学专家罗伯特·兰登在法国巴黎出差期间的一个午夜接到一个紧急电话，得知卢浮宫博物馆年迈的馆长被人杀害在卢浮宫的博物馆里，人们在他的尸体旁边发现了一个难以捉摸的密码...");
						content.setImageUrl("" + R.drawable.c4);
						content
								.setUrl("http://dev2.mopietek.net:8080/mp4/320480flv.3gp");
						content
								.setHeightUrl("http://dev2.mopietek.net:8080/mp4/xyx.3gp");
						content
								.setWidthUrl("http://dev2.mopietek.net:8080/mp4/480320flv.3gp");
						items.add(content);
					}

					@Override
					public View createTabContent(String tag) {
						ListView listView = new ListView(CustomerPlayer.this);
						listView.setAdapter(new ContentAdapter(
								CustomerPlayer.this, 0, items));
						listView.setBackgroundResource(R.color.white);
						listView.setCacheColorHint(Color.WHITE);
						listView.setDivider(getResources().getDrawable(
								R.drawable.back));
						listView.setDividerHeight(1);
						return listView;
					}
				}));
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				TabWidget tabWidget = (TabWidget) tabHost.getTabWidget();
				if (tabId.equals("tab1")) {
					View vie = (View) tabWidget.getChildAt(0);
					vie.setBackgroundResource(R.drawable.back);

					View vie1 = (View) tabWidget.getChildAt(1);
					vie1.setBackgroundResource(R.drawable.tabhost);

					ImageView iv = (ImageView) vie
							.findViewById(android.R.id.icon);
					iv.setImageDrawable(getResources().getDrawable(
							R.drawable.menu2));
					ImageView iv1 = (ImageView) vie1
							.findViewById(android.R.id.icon);
					iv1.setImageDrawable(getResources().getDrawable(
							R.drawable.vod2));

				}
				if (tabId.equals("tab2")) {
					View vie = (View) tabWidget.getChildAt(1);
					vie.setBackgroundResource(R.drawable.back);
					View vie1 = (View) tabWidget.getChildAt(0);
					vie1.setBackgroundResource(R.drawable.tabhost);

					ImageView iv = (ImageView) vie
							.findViewById(android.R.id.icon);
					iv.setImageDrawable(getResources().getDrawable(
							R.drawable.vod));
					ImageView iv1 = (ImageView) vie1
							.findViewById(android.R.id.icon);
					iv1.setImageDrawable(getResources().getDrawable(
							R.drawable.menu));
				}
			}
		});

		TabWidget tabWidget = (TabWidget) tabHost.getTabWidget();
		View vie = (View) tabWidget.getChildAt(0);
		vie.setBackgroundResource(R.drawable.back);
		View vie1 = (View) tabWidget.getChildAt(1);
		vie1.setBackgroundResource(R.drawable.tabhost);
	}

	// 添加菜单项
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "主页").setIcon(
				getResources().getDrawable(R.drawable.m1));
		menu.add(0, 2, 1, "后退").setIcon(
				getResources().getDrawable(R.drawable.m2));
		menu.add(0, 3, 2, "详细").setIcon(
				getResources().getDrawable(R.drawable.m3));
		return super.onCreateOptionsMenu(menu);
	}

	private class ListAdapter extends ArrayAdapter<String> {
		private List<String> list;

		public ListAdapter(Context context, int textViewResourceId,
				List<String> list) {
			super(context, textViewResourceId, list);
			this.list = list;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			final String content = list.get(position);
			if (view == null) {
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.listcontent, null);
			}
			if (content != null) {
				TextView title = (TextView) view
						.findViewById(R.id.ContentTitle);
				title.setTextColor(Color.BLACK);
				title.setText(Html.fromHtml(content));
				ImageView imageView = (ImageView) view.findViewById(R.id.image);
				imageView.setImageDrawable(CustomerPlayer.this.getResources()
						.getDrawable(R.drawable.star));
			}
			return view;
		}

	}

	private class ContentAdapter extends ArrayAdapter<Content> {
		private List<Content> items;

		public ContentAdapter(Context context, int textViewResourceId,
				List<Content> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			final Content content = items.get(position);
			if (view == null) {
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				// 判断手机屏幕的方向
				DisplayMetrics dm = new DisplayMetrics();
				getWindowManager().getDefaultDisplay().getMetrics(dm);
				int width = dm.widthPixels;
				int heigh = dm.heightPixels;
				if (width / heigh > 0) {
					view = inflater.inflate(R.layout.content_horizontal, null);
				}
				if (width / heigh == 0) {
					view = inflater.inflate(R.layout.content, null);
				}
			}
			if (content != null) {
				TextView title = (TextView) view
						.findViewById(R.id.ContentTitle);
				TextView comment = (TextView) view
						.findViewById(R.id.ContentComment);
				ImageView imageView = (ImageView) view.findViewById(R.id.image);
				title.setTextColor(Color.BLACK);
				comment.setTextColor(R.drawable.back);
				title.setText(content.getTitle());
				comment.setText("类型：动画/喜剧");
				imageView.setImageDrawable(CustomerPlayer.this.getResources()
						.getDrawable(Integer.parseInt(content.getImageUrl())));

				ImageView imageButton = (ImageView) view
						.findViewById(R.id.playimage);
				imageButton.setImageDrawable(getResources().getDrawable(
						R.drawable.play));
				imageButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(CustomerPlayer.this, PlayMp4.class);
						// 传递数据
						Bundle bundle = new Bundle();
						bundle.putString("heighturl", content.getHeightUrl());
						bundle.putString("widthurl", content.getWidthUrl());
						intent.putExtras(bundle);
						startActivity(intent);
					}
				});

				ImageView imageInfo = (ImageView) view.findViewById(R.id.info);
				imageInfo.setImageDrawable(getResources().getDrawable(
						R.drawable.info));

				ImageView imageInfo1 = (ImageView) view
						.findViewById(R.id.info1);
				imageInfo1.setImageDrawable(getResources().getDrawable(
						R.drawable.info));

				ImageView imageInfo2 = (ImageView) view
						.findViewById(R.id.info2);
				imageInfo2.setImageDrawable(getResources().getDrawable(
						R.drawable.info));

				ImageView imageInfo3 = (ImageView) view
						.findViewById(R.id.info3);
				imageInfo3.setImageDrawable(getResources().getDrawable(
						R.drawable.info));
				// 星星
				RatingBar mSmallRatingBar = (RatingBar) view
						.findViewById(R.id.ratingBar);
				if (mSmallRatingBar != null) {
					mSmallRatingBar.setRating(3);
				}
			}
			return view;
		}
	}
	
	@Override 
    public boolean onKeyDown(int keyCode, KeyEvent event) { 
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { 
        	finish();
            return true; 
        } 
        return false; 
    }
}
