package com.easymorse.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 演示带CheckBox的ListView
 * 
 * @author marshal
 * 
 */
public class ListViewDemoActivity extends Activity {

	/**
	 * 自定义的用于观察的对象类，当ListView中的元素监控到长按时通知所有元素显示或者不显示CheckBox以及toolbar
	 * 
	 * @author marshal
	 * 
	 */
	class MyObservable extends Observable {
		public void toobarStatusChanged(boolean visible) {
			setChanged();
			notifyObservers(visible);
		}
	}

	// 每行显示几个图片
	private static final int ROW_ELEMENTS_SIZE = 5;

	// 本例中的ListView
	private ListView myListView;

	// checkbox是否可见的标志位
	private boolean checkItemVisible;

	private ViewGroup toolbar;

	// 存放选中的checkbox条目的图片列表下标
	private Set<Integer> checkedIds = new HashSet<Integer>();

	// 创建观察对象
	private MyObservable observable = new MyObservable();

	// 演示用的图片数据集合
	@SuppressWarnings("serial")
	private List<Integer> drawables = new ArrayList<Integer>() {
		{
			add(R.drawable.defense_mechanism);
			add(R.drawable.gzorah);
			add(R.drawable.jay_z_linkin_park);
			add(R.drawable.korn);
			add(R.drawable.defense_mechanism);
			add(R.drawable.gzorah);
			add(R.drawable.jay_z_linkin_park);
			add(R.drawable.korn);
			add(R.drawable.defense_mechanism);
			add(R.drawable.gzorah);
			add(R.drawable.jay_z_linkin_park);
			add(R.drawable.korn);
			add(R.drawable.defense_mechanism);
			add(R.drawable.gzorah);
			add(R.drawable.jay_z_linkin_park);
			add(R.drawable.korn);
			add(R.drawable.defense_mechanism);
			add(R.drawable.gzorah);
			add(R.drawable.defense_mechanism);
			add(R.drawable.gzorah);
			add(R.drawable.jay_z_linkin_park);
			add(R.drawable.korn);
			add(R.drawable.defense_mechanism);
			add(R.drawable.gzorah);
			add(R.drawable.jay_z_linkin_park);
			add(R.drawable.korn);
			add(R.drawable.defense_mechanism);
			add(R.drawable.gzorah);
			add(R.drawable.jay_z_linkin_park);
			add(R.drawable.korn);
			add(R.drawable.defense_mechanism);
			add(R.drawable.gzorah);
			add(R.drawable.jay_z_linkin_park);
			add(R.drawable.korn);
		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		myListView = (ListView) this.findViewById(R.id.myList);
		myListView.addFooterView(View.inflate(this, R.layout.footer, null));

		toolbar = (ViewGroup) this.findViewById(R.id.toolbar);

		// 设置选择按钮
		Button button = (Button) this.findViewById(R.id.addToButton);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(ListViewDemoActivity.this, "选择了：" + checkedIds,
						1000).show();
			}
		});

		// 设置删除按钮
		button = (Button) this.findViewById(R.id.removeButton);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/**
				 * 这里看起来比较繁琐，实际上是为了防止删错记录 checkedIds是Set，是为了避免重复记录元素下标
				 * 设置为List是为了排序，而且要设置为倒序，目的是从后面记录往前面记录删不会有错
				 */
				List<Integer> list = new ArrayList<Integer>(checkedIds);
				Collections.sort(list);
				Collections.reverse(list);

				for (int i : list) {
					drawables.remove(i);
				}

				checkedIds.clear();

				/**
				 * 获得ListView的子视图，也即能在屏幕中看到的列表行 迭代删除它们的内容，然后重新生成视图内容
				 */
				for (int i = 0; i < myListView.getChildCount(); i++) {
					ViewGroup viewgroup = (ViewGroup) myListView.getChildAt(i);
					viewgroup.removeAllViews();
					generateRowElements(
							myListView.getPositionForView(viewgroup), viewgroup);
				}
			}
		});

		// 自定义的ListView适配器
		myListView.setAdapter(new BaseAdapter() {

			@Override
			public View getView(final int position, View convertView,
					ViewGroup parent) {
				// 创建ListView行的布局
				ViewGroup layout = (ViewGroup) View.inflate(
						ListViewDemoActivity.this, R.layout.row, null);
				generateRowElements(position, layout);
				return layout;
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public Object getItem(int position) {
				return drawables.get(position);
			}

			@Override
			public int getCount() {
				// ceil取大于输入值的最小整数，比如输入3.1，则返回4
				return (int) Math.ceil(drawables.size()
						/ (float) ROW_ELEMENTS_SIZE);
			}
		});

		myListView.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK && checkItemVisible) {
					checkItemVisible = false;
					checkedIds.clear();// 清除选择checkbox的id
					observable.toobarStatusChanged(false);
					return true;
				}
				return false;// 其他情况下不处理，使用系统对回退键的处理，即退出应用
			}
		});

	}

	/**
	 * 根据行号（position）生成ListView行的内容
	 * 
	 * @param position
	 * @param layout
	 */
	private void generateRowElements(final int position, ViewGroup layout) {
		//防止删除部分内容后该行已经不存在的情况下报错
		if (position * ROW_ELEMENTS_SIZE > drawables.size() - 1) {
			return;
		}
		// 从图片列表中选取本行需要的子列表
		List<Integer> sub = drawables.subList(position * ROW_ELEMENTS_SIZE,
				Math.min((position + 1) * ROW_ELEMENTS_SIZE, drawables.size()));

		for (int i = 0; i < sub.size(); i++) {
			// 创建元素的视图
			final View view = View.inflate(ListViewDemoActivity.this,
					R.layout.element, null);
			Integer id = sub.get(i);

			// 图片列表的下标
			final Integer index = position * ROW_ELEMENTS_SIZE + i;

			// 获取布局中的ImageView，然后赋值图片
			ImageView imageView = (ImageView) view.findViewById(R.id.cdImage);
			imageView.setImageResource(id);

			// 获取TextView，然后赋值文字
			TextView textView = (TextView) view.findViewById(R.id.cdTitle);
			textView.setText("柴可夫司机");

			// 获取Checkbox
			final CheckBox checkBox = (CheckBox) view
					.findViewById(R.id.checkItem);
			// 如果在选择列表中有，设置为选取
			checkBox.setChecked(checkedIds.contains(index));

			// 创建观察者，用于监控是否有Checkbox可见性事件，然后加入到可观察对象中
			Observer observer = new Observer() {
				@Override
				public void update(Observable observable, Object data) {
					checkBox.setVisibility((Boolean) data ? View.VISIBLE
							: View.INVISIBLE);
					toolbar.setVisibility((Boolean) data ? View.VISIBLE
							: View.INVISIBLE);
					checkBox.setChecked(checkedIds.contains(index));
				}
			};
			observable.addObserver(observer);

			// checkbox增加监听器，监控勾选状态
			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					if (isChecked) {
						checkedIds.add(index);
					} else {
						checkedIds.remove(index);
					}
				}
			});

			// 设置checkbox的可见性
			if (checkItemVisible) {
				checkBox.setVisibility(View.VISIBLE);
			}

			// 设置长按监听器
			view.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					checkItemVisible = !checkItemVisible;
					observable.toobarStatusChanged(checkItemVisible);
					return true;
				}
			});

			layout.addView(view);
		}
	}
}