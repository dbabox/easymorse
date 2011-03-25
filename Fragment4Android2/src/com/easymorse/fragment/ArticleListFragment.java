package com.easymorse.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ArticleListFragment extends ListFragment {

	private static final List<Member> MEMBERS = new ArrayList<Member>();

	static {
		Member member = new Member("Marshal",
				"WitMob团队负责人。致力于通过集体的力量使团队成长为国内先进的移动互联网外包应用提供商。",
				R.drawable.marshal, "http://marshal.easymorse.com/");
		MEMBERS.add(member);
		member = new Member("王保安", "iOS组负责人。擅长iOS开发。在工作上乐观勤奋，虽然有时碰到挫折也想回唐山老家。",
				R.drawable.wangjun, "http://wangjun.easymorse.com/");
		MEMBERS.add(member);
		member = new Member("大辉", "大嘴帅锅，Android组负责人。目前主攻Android技术方向。",
				R.drawable.dahui, "http://bigcat.easymorse.com/");
		MEMBERS.add(member);
	}

	private MemberListener memberListener;

	public void setMemberListener(MemberListener memberListener) {
		this.memberListener = memberListener;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setListAdapter(new ArticleListAdapter());
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		l.setItemChecked(position, true);

		if (memberListener != null) {
			memberListener.onMemberSelected(MEMBERS.get(position));
		}
	}

	class ArticleListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return MEMBERS.size();
		}

		@Override
		public Object getItem(int position) {
			return MEMBERS.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.row, null);
			}

			Member member = MEMBERS.get(position);

			TextView textView = (TextView) convertView
					.findViewById(R.id.authorName);
			textView.setText(member.getName());

			textView = (TextView) convertView
					.findViewById(R.id.authorIntroduction);
			textView.setText(member.getIntroduction());

			if (member.getImageResourseId() != 0) {
				ImageView imageView = (ImageView) convertView
						.findViewById(R.id.authorImage);
				imageView.setImageResource(member.getImageResourseId());
			}

			return convertView;
		}

	}
}
