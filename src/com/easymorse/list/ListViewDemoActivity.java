package com.easymorse.list;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 演示带CheckBox的ListView
 * 
 * @author marshal
 * 
 */
public class ListViewDemoActivity extends Activity {

	/**
	 * 自定义的用于观察的对象类，当ListView中的元素监控到长按时通知所有元素显示CheckBox
	 * 
	 * @author marshal
	 * 
	 */
	class MyObservable extends Observable {
		public void checkboxChanged(boolean visible) {
			setChanged();
			notifyObservers(visible);
		}
	}

	// 本例中的ListView
	private ListView myListView;

	// checkbox是否可见的标志位
	private boolean checkItemVisible;

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

		// 自定义的ListView适配器
		myListView.setAdapter(new BaseAdapter() {

			// 每行显示几个图片
			private static final int ROW_ELEMENTS_SIZE = 5;

			@Override
			public View getView(final int position, View convertView,
					ViewGroup parent) {
				Log.d("listview", "position: >>>" + position);

				// 创建ListView行的布局
				ViewGroup layout = (ViewGroup) View.inflate(
						ListViewDemoActivity.this, R.layout.row, null);

				// 从图片列表中选取本行需要的子列表
				List<Integer> sub = drawables.subList(
						position * ROW_ELEMENTS_SIZE,
						Math.min((position + 1) * ROW_ELEMENTS_SIZE,
								drawables.size() - 1));

				for (int i = 0; i < sub.size(); i++) {
					// 创建元素的视图
					final View view = View.inflate(ListViewDemoActivity.this,
							R.layout.element, null);
					Integer id = sub.get(i);

					// 图片列表的下标
					final Integer index = position * ROW_ELEMENTS_SIZE + i;

					// 获取布局中的ImageView，然后赋值图片
					ImageView imageView = (ImageView) view
							.findViewById(R.id.cdImage);
					imageView.setImageResource(id);

					// 获取TextView，然后赋值文字
					TextView textView = (TextView) view
							.findViewById(R.id.cdTitle);
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
							if (!checkItemVisible) {
								checkItemVisible = true;
								observable.checkboxChanged(true);
							}
							return true;
						}
					});

					layout.addView(view);
				}

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
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					checkItemVisible = false;
					checkedIds.clear();// 清除选择checkbox的id
					observable.checkboxChanged(false);
					return true;
				}
				return false;
			}
		});

	}

}