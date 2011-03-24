package com.easymorse.fragment;

import android.app.Activity;
import android.os.Bundle;

public class FragmentDemoActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
		final ContentFragment contentFragment = (ContentFragment) getFragmentManager()
				.findFragmentById(R.id.content);
		contentFragment.loadUrl("http://marshal.easymorse.com");
		
		ArticleListFragment articleListFragment = (ArticleListFragment) getFragmentManager()
				.findFragmentById(R.id.list);
		articleListFragment.setMemberListener(new MemberListener() {
			
			@Override
			public void onMemberSelected(Member member) {
				contentFragment.loadUrl(member.getBlogUrl());
			}
		});
    }
}