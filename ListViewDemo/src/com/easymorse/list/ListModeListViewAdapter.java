package com.easymorse.list;

import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.easymorse.list.datasource.Page;
import com.easymorse.list.datasource.Record;

/**
 * 列表模式适配器
 * 
 * @author marshal
 * 
 */
public class ListModeListViewAdapter extends AbstractListViewAdapter {

	public ListModeListViewAdapter(ListViewModel model) {
		super(model);
	}

	protected void getInternalView(ViewGroup layout, Page page) {
		final Record record = page.getResults().get(0);

		ViewGroup viewGroup = (ViewGroup) View.inflate(model.context,
				R.layout.element_list_mode, null);
		TextView textView = (TextView) viewGroup.findViewById(R.id.cdTitle);
		textView.setText(record.getName() + " - " + record.getId());
		layout.addView(viewGroup);

		// 获取Checkbox
		CheckBox checkBox = (CheckBox) viewGroup.findViewById(R.id.checkItem);
		// 如果在选择列表中有，设置为选取
		checkBox.setChecked(model.checkedIds.contains(record.getId()));

		// checkbox增加监听器，监控勾选状态
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					model.checkedIds.add(record.getId());
				} else {
					model.checkedIds.remove(record.getId());
				}
			}
		});

		// 设置checkbox的可见性
		if (model.checkItemVisible) {
			checkBox.setVisibility(View.VISIBLE);
		}

		// 设置长按监听器
		viewGroup.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				model.checkItemVisible = !model.checkItemVisible;
				model.listViewUIChangeObservable
						.toolbarStatusChanged(model.checkItemVisible);
				return true;
			}
		});
	}

	@Override
	protected int getPageSize() {
		return 1;
	}

}
