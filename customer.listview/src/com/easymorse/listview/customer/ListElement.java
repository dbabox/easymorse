package com.easymorse.listview.customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public interface ListElement {
	public int getLayoutId();

	public boolean isClickable();

	public View getViewForListElement(LayoutInflater layoutInflater,
			Context context, View view);
}
