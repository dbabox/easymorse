package com.easymorse.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class FragmentDemoActivity extends FragmentActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final ContentFragment contentFragment = (ContentFragment) getSupportFragmentManager()
				.findFragmentById(R.id.content);
		contentFragment.loadUrl("http://marshal.easymorse.com");

		ArticleListFragment articleListFragment = (ArticleListFragment) getSupportFragmentManager()
				.findFragmentById(R.id.list);
		articleListFragment.setMemberListener(new MemberListener() {

			@Override
			public void onMemberSelected(Member member) {
				contentFragment.loadUrl(member.getBlogUrl());
			}
		});
	}
}