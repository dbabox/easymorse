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
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ListViewDemoActivity extends Activity {
	
	class MyObservable extends Observable{
		public void checkboxChanged(View checkbox){
			setChanged();
			notifyObservers(checkbox);
		}
	}

	private ListView myListView;

	private MyObservable observable = new MyObservable();

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
		myListView.setAdapter(new BaseAdapter() {

			private static final int ROW_ELEMENTS_SIZE = 5;

			private boolean checkItemVisible;

			private Set<Integer> checkedIds = new HashSet<Integer>();
			
			@Override
			public View getView(final int position, View convertView,
					ViewGroup parent) {
				Log.d("listview", "position: >>>" + position);
				ViewGroup layout = (ViewGroup) View.inflate(
						ListViewDemoActivity.this, R.layout.row, null);
				List<Integer> sub = drawables.subList(
						position * ROW_ELEMENTS_SIZE,
						Math.min((position + 1) * ROW_ELEMENTS_SIZE,
								drawables.size() - 1));

				for (int i = 0; i < sub.size(); i++) {
					final View view = View.inflate(ListViewDemoActivity.this,
							R.layout.element, null);
					Integer id = sub.get(i);
					final Integer index = position * ROW_ELEMENTS_SIZE + i;
					view.setTag(index);

					ImageView imageView = (ImageView) view
							.findViewById(R.id.cdImage);
					imageView.setImageResource(id);
					TextView textView = (TextView) view
							.findViewById(R.id.cdTitle);
					textView.setText("柴可夫司机");

					final CheckBox checkBox = (CheckBox) view
							.findViewById(R.id.checkItem);
					if (checkedIds.contains(index)) {
						checkBox.setChecked(true);
					}
					
					Observer observer=new Observer() {
						@Override
						public void update(Observable observable, Object data) {
							checkBox.setVisibility(View.VISIBLE);
						}
					};
					
					observable.addObserver(observer);
					
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
					if (checkItemVisible) {
						checkBox.setVisibility(View.VISIBLE);
					}

					view.setOnLongClickListener(new OnLongClickListener() {

						@Override
						public boolean onLongClick(View v) {
							checkItemVisible = true;
							observable.checkboxChanged(v);
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
				return (int) Math.ceil(drawables.size()
						/ (float) ROW_ELEMENTS_SIZE);
			}
		});

	}

}