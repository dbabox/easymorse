package com.easymorse.list;

import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.easymorse.list.datasource.Record;

/**
 * 演示带CheckBox的ListView
 * 
 * @author marshal
 * 
 */
public class ListViewDemoActivity extends Activity {

	class TagSetupDialog extends Dialog {

		public TagSetupDialog(Context context) {
			super(context);
		}

	}

	/**
	 * 自定义对话框
	 * 
	 * @author marshal
	 * 
	 */
	class ViewSetupDialog extends Dialog {

		public ViewSetupDialog(Context context) {
			super(context);
		}

		public ViewSetupDialog(Context context, boolean cancelable,
				OnCancelListener cancelListener) {
			super(context, cancelable, cancelListener);
		}

		public ViewSetupDialog(Context context, int theme) {
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

	// mvc模式里的model
	private ListViewModel model = new ListViewModel();

	private BaseAdapter listModeListViewAdapter;

	private BaseAdapter thumderModeListViewAdapter;

	// 本例中的ListView
	private ListView myListView;

	private ViewGroup toolbar;

	private ViewSetupDialog viewSetupDialog;

	private TagSetupDialog tagSetupDialog;

	private ViewGroup listContailer;

	private void initListView() {
		if (model.chooseViewModeId == R.id.viewModeThumder) {
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

		// 设置model对象
		model = new ListViewModel();
		model.context = this;

		// 设置listview的adapter
		this.listModeListViewAdapter = new ListModeListViewAdapter(model);
		this.thumderModeListViewAdapter = new ThumderModeListViewAdapter(model);

		this.listContailer = (ViewGroup) this.findViewById(R.id.listContainer);

		/**
		 * 视图对话框
		 */
		viewSetupDialog = new ViewSetupDialog(this);
		viewSetupDialog.setContentView(R.layout.view_dialog);
		viewSetupDialog.setTitle("对话框");

		/**
		 * tag对话框
		 */
		tagSetupDialog = new TagSetupDialog(this);
		tagSetupDialog.setContentView(R.layout.tag_dialog);
		tagSetupDialog.setTitle("选择tag");

		GridView gridView = (GridView) tagSetupDialog
				.findViewById(R.id.tagsGrid);
		gridView.setAdapter(new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				TextView textView = new TextView(ListViewDemoActivity.this);
				textView.setText("" + position);
				registerForContextMenu(textView);
				return textView;
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public Object getItem(int position) {
				return null;
			}

			@Override
			public int getCount() {
				return 100;
			}
		});

		// radio按钮改变监听器，以下2组radio按钮共用一个监听器
		RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				model.elementChangeObservable.listViewNeedChange(checkedId);
			}
		};

		// 设置视图模式
		RadioGroup radioGroup = (RadioGroup) viewSetupDialog
				.findViewById(R.id.viewModeRadioGroup);
		radioGroup.check(model.chooseViewModeId);
		radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);

		radioGroup = (RadioGroup) viewSetupDialog
				.findViewById(R.id.sortByRadioGroup);
		radioGroup.check(model.chooseSortById);
		radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);

		// 设置tag按钮
		Button button = (Button) viewSetupDialog.findViewById(R.id.chooseTags);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tagSetupDialog.show();
			}
		});

		model.elementChangeObservable.addObserver(new Observer() {

			@Override
			public void update(Observable observable, Object data) {
				int id = (Integer) data;

				switch (id) {
				// view mode
				case R.id.viewModeList:
				case R.id.viewModeThumder:
					model.chooseViewModeId = (Integer) data;
					break;
				case R.id.sortByPlayAlpha:
					model.page.setOrderFieldName(Record.ORDER_BY_NAME);
					// 可在这里设置名称升序
					break;
				case R.id.sortByPlayArtist:
					// TODO 没写呢
					break;
				case R.id.sortByPlayTimes:
					model.page.setOrderFieldName(Record.ORDER_BY_PLAY_TIMES);
					// 可在这里设置播放次数降序
					break;
				default:
				}
				initListView();
				viewSetupDialog.hide();
			}
		});

		toolbar = (ViewGroup) this.findViewById(R.id.toolBar);
		button = (Button) toolbar.findViewById(R.id.orderButton);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				viewSetupDialog.show();
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

		// 设置长按监听器
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

		initListView();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, 1, 0, "Edit");
		menu.add(0, 2, 0, "Delete");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		Log.d(TAG, ">>>>selected....");
		return super.onContextItemSelected(item);
	}

}