package com.easymorse;

import java.util.ArrayList;
import java.util.List;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabContentFactory;

public class CustomerPlayer extends TabActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		final TabHost tabHost = getTabHost();

		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("栏目内容")
				.setContent(new TabContentFactory() {

					List<Content> items;

					{
						items = new ArrayList<Content>();
						Content content = new Content();
						content.setTitle("阿童木");
						content
								.setComment("因意外痛失爱子的天才科学家天马博士悲痛欲绝，萌生了复制爱子的想法并成功创造了机器人阿童木，他和人类一样拥有喜怒哀乐，而且还有博士儿子残余的情感与记忆。但阿童木却无法令抑郁的博士重展欢颜，博士狠心决定将他遗弃...");
						content.setImageUrl("" + R.drawable.c1);
						items.add(content);

						content = new Content();
						content.setTitle("三枪拍案惊奇");
						content
								.setComment("故事发生于古代某大漠之中，一家面馆老板名为王五麻子，为人阴险吝啬，如此人品，老婆自然不待见。恰好伙计李四长得很帅又有幽默感，两人一拍即合，有了私情。王五发现两人私情后...");
						content.setImageUrl("" + R.drawable.c2);
						items.add(content);

						content = new Content();
						content.setTitle("花木兰");
						content
								.setComment("在古老的中国，有一位个性爽朗，性情善良的好女孩，名字叫作「花木兰」，身为花家的大女儿，花木兰在父母开明的教悔下，一直很期待自己能花家带来荣耀。不过就在北方匈奴来犯...");
						content.setImageUrl("" + R.drawable.c3);
						items.add(content);

						content = new Content();
						content.setTitle("达芬奇密码");
						content
								.setComment("本片讲述了哈佛大学的符号学专家罗伯特·兰登在法国巴黎出差期间的一个午夜接到一个紧急电话，得知卢浮宫博物馆年迈的馆长被人杀害在卢浮宫的博物馆里，人们在他的尸体旁边发现了一个难以捉摸的密码...");
						content.setImageUrl("" + R.drawable.c4);
						items.add(content);
					}

					@Override
					public View createTabContent(String tag) {
						ListView listView = new ListView(CustomerPlayer.this);
						listView.setAdapter(new ContentAdapter(
								CustomerPlayer.this, 0, items));

						return listView;
					}
				}));

		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("栏目列表")
				.setContent(new Intent(this, ItemList.class)));
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

			if (view == null) {
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.content, null);
			}

			Content content = items.get(position);
			if (content != null) {
				TextView title = (TextView) view
						.findViewById(R.id.ContentTitle);
				TextView comment = (TextView) view
						.findViewById(R.id.ContentComment);
				ImageView imageView = (ImageView) view.findViewById(R.id.image);

				title.setText(content.getTitle());
				comment.setText(content.getComment());
				imageView.setImageDrawable(CustomerPlayer.this.getResources()
						.getDrawable(Integer.parseInt(content.getImageUrl())));
			}

			return view;
		}
	}
}
