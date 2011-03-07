package com.easymorse.demos;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FragmentDemoActivity extends FragmentActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		FragmentManager fragmentManager=getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		DemoLeftFragment leftFragment=new DemoLeftFragment();
		DemoRightFramgment rightFramgment=new DemoRightFramgment();
		fragmentTransaction.add(R.id.leftView, leftFragment);
		fragmentTransaction.add(R.id.rightView, rightFramgment);
		fragmentTransaction.commit();
	}

}