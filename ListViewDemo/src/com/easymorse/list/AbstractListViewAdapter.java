package com.easymorse.list;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.easymorse.list.datasource.Page;
import com.easymorse.list.datasource.Record;
import com.easymorse.list.datasource.RecordDao;

/**
 * 用于ListViewAdapter的实现，抽象实现。
 * 
 * 有两种实现，图模式和列表模式。二者有很多相同的逻辑，通过抽象类来复用。也便于理解。
 * 
 * @author marshal
 *
 */
public abstract class AbstractListViewAdapter extends BaseAdapter {
	
	@SuppressWarnings("unused")
	private static final String TAG = "listview";

	/**
	 * 
	 * @return 每页显示几条记录。子类会有区别。
	 */
	protected abstract int getPageSize();

	protected RecordDao dao;

	protected ListViewModel model;
	
	public AbstractListViewAdapter(ListViewModel model){
		this.model=model;
		this.dao=RecordDao.getRecordDao(model.context);
	}

	@Override
	public int getCount() {
		Page page = model.page;
		page.setSize(getPageSize());
		page.setNo(1);
		dao.browse(page);
		return page.getPageCount();
	}

	@Override
	public Object getItem(int position) {
		Page page =model.page;
		page.setNo(position + 1);
		page.setSize(getPageSize());
		dao.browse(page);
		return page;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewGroup layout = (ViewGroup) View.inflate(this.model.context,
				R.layout.row, null);

		Page page = model.page;
		page.setNo(position + 1);
		page.setSize(getPageSize());
		page.setOrderFieldName(Record.ORDER_BY_PLAY_TIMES);
		page.setOrderDesc(true);
		dao.browse(page);

		if (!page.getResults().isEmpty()) {
			getInternalView(layout, page);
		}

		return layout;
	}

	/**
	 * 子类会有不同实现
	 * @param layout
	 * @param page
	 */
	protected abstract void getInternalView(ViewGroup layout, Page page);
}
