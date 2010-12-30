package com.easymorse.list;

import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.easymorse.list.datasource.Page;
import com.easymorse.list.datasource.Record;

/**
 * 图模式listview
 * 
 * @author marshal
 * 
 */
public class ThumderModeListViewAdapter extends AbstractListViewAdapter {

	public ThumderModeListViewAdapter(ListViewModel model) {
		super(model);
	}

	/**
	 * 根据行号（position）生成ListView行的内容
	 * 
	 * @param position
	 * @param layout
	 */

	@Override
	protected int getPageSize() {
		return 5;
	}

	@Override
	protected void getInternalView(ViewGroup layout, Page page) {
		for (final Record record : page.getResults()) {
			// 创建元素的视图
			final View view = View.inflate(model.context, R.layout.element,
					null);
			// 获取布局中的ImageView，然后赋值图片
			ImageView imageView = (ImageView) view.findViewById(R.id.cdImage);
			imageView.setImageDrawable(dao.getDrawable(record.getId()));

			// 获取TextView，然后赋值文字
			TextView textView = (TextView) view.findViewById(R.id.cdTitle);
			textView.setText(record.getName() + " - " + record.getId());

			// 获取Checkbox
			CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkItem);
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

			// 设置长按监听器 TODO 这里写的不好，重复了，而且是否会造成内存泄露？
			view.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					model.checkItemVisible = !model.checkItemVisible;
					model.listViewUIChangeObservable
							.toolbarStatusChanged(model.checkItemVisible);
					return true;
				}
			});

			layout.addView(view);
		}

	}
}
