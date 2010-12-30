package com.easymorse.list;

import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioGroup;

/**
 * 演示带CheckBox的ListView
 * 
 * @author marshal
 * 
 */
public class ListViewDemoActivity extends Activity {

	/**
	 * 自定义对话框
	 * 
	 * @author marshal
	 * 
	 */
	class MyDialog extends Dialog {

		public MyDialog(Context context) {
			super(context);
		}

		public MyDialog(Context context, boolean cancelable,
				OnCancelListener cancelListener) {
			super(context, cancelable, cancelListener);
		}

		public MyDialog(Context context, int theme) {
			super(context, theme);
		}

		/**
		 * 处理触摸事件，如果触摸超出对话框范围，则对话框退出
		 */
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			Rect rect = new Rect();
			View view = findViewById(R.id.dialogLayout);// 取对话框最外层视图
			view.getWindowVisibleDisplayFrame(rect);// 得到window的矩形

			// 因为对话框居中，因此对话框相对于window的坐标可通过对话框长和宽以及window矩形计算出来
			Rect rect2 = new Rect((rect.right - view.getRight()) / 2,
					(rect.bottom - view.getBottom()) / 2, view.getRight()
							+ (rect.right - view.getRight()) / 2,
					view.getBottom() + (rect.bottom - view.getBottom()));

			if (!rect2.contains((int) event.getRawX(), (int) event.getRawY())) {
				this.dismiss();
				return true;
			}
			return super.onTouchEvent(event);
		}
	}

	@SuppressWarnings("unused")
	private static final String TAG = "listview";

	//mvc模式里的model
	private ListViewModel model = new ListViewModel();

	private BaseAdapter listModeListViewAdapter;

	private BaseAdapter thumderModeListViewAdapter;

	// 本例中的ListView
	private ListView myListView;

	private ViewGroup toolbar;

	private MyDialog dialog;

	private RadioGroup viewModeRadioGroup;

	private ViewGroup listContailer;

	private void initListView(int radioId) {
		if (radioId == R.id.viewModeThumder) {
			// 自定义的ListView适配器,thumder模式
			myListView.setAdapter(thumderModeListViewAdapter);

		} else {
			// 自定义的ListView适配器，list
			myListView.setAdapter(listModeListViewAdapter);
		}
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		//设置model对象
		model = new ListViewModel();
		model.context = this;
		
		//设置listview的adapter
		this.listModeListViewAdapter = new ListModeListViewAdapter(model);
		this.thumderModeListViewAdapter = new ThumderModeListViewAdapter(model);

		this.listContailer = (ViewGroup) this.findViewById(R.id.listContainer);

		/**
		 * 视图对话框
		 */
		dialog = new MyDialog(this);
		dialog.setContentView(R.layout.view_dialog);
		dialog.setTitle("对话框");
		viewModeRadioGroup = (RadioGroup) dialog
				.findViewById(R.id.viewModeRadioGroup);
		viewModeRadioGroup.check(R.id.viewModeThumder);

		viewModeRadioGroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						model.elementChangeObservable
								.listViewNeedChange(new ListViewNeedChangeData(
										ListViewNeedChangeData.TYPE_VIEW_MODE,
										checkedId));
					}
				});

		model.elementChangeObservable.addObserver(new Observer() {

			@Override
			public void update(Observable observable, Object data) {
				if (data instanceof ListViewNeedChangeData) {
					ListViewNeedChangeData _data = (ListViewNeedChangeData) data;
					switch (_data.getType()) {
					case ListViewNeedChangeData.TYPE_VIEW_MODE:
						initListView((Integer) _data.data);
						dialog.hide();
						break;
					default:
					}
				}
			}
		});

		toolbar = (ViewGroup) this.findViewById(R.id.toolBar);
		Button button = (Button) toolbar.findViewById(R.id.orderButton);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.show();
			}
		});

		// 监听，当得到观察者通知，就改变当前屏幕下所有行的checkbox的可见行
		model.listViewUIChangeObservable.addObserver(new Observer() {

			@Override
			public void update(Observable observable, Object data) {
				model.checkedIds.clear();
				for (int i = 0; i < myListView.getChildCount(); i++) {
					ViewGroup rowViewGroup = (ViewGroup) myListView
							.getChildAt(i);
					for (int j = 0; j < rowViewGroup.getChildCount(); j++) {
						ViewGroup elementViewGroup = (ViewGroup) rowViewGroup
								.getChildAt(j);
						CheckBox checkBox = (CheckBox) elementViewGroup
								.findViewById(R.id.checkItem);
						checkBox.setChecked(false);
						checkBox.setVisibility((Boolean) data ? View.VISIBLE
								: View.INVISIBLE);
					}
				}
			}
		});

		/**
		 * 设置listview
		 */
		myListView = (ListView) View.inflate(this, R.layout.listview, null);
		this.listContailer.addView(myListView);
		
		//设置长按监听器
		myListView.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK && model.checkItemVisible) {
					model.checkItemVisible = false;
					model.checkedIds.clear();// 清除选择checkbox的id
					model.listViewUIChangeObservable
							.toolbarStatusChanged(false);
					return true;
				}
				return false;// 其他情况下不处理，使用系统对回退键的处理，即退出应用
			}
		});
		
		initListView(viewModeRadioGroup.getCheckedRadioButtonId());
	}

}