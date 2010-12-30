package com.easymorse.list;

/**
 * listview改变时的一些数据，封装了对象。比如是显示形式变化，还是排序，还是过滤
 * 
 * @author marshal
 * 
 */
public class ListViewNeedChangeData {

	static final int TYPE_VIEW_MODE = 1;

	private int type;

	Object data;

	public ListViewNeedChangeData(int type, Object data) {
		this.type = type;
		this.data = data;
	}

	public int getType() {
		return type;
	}

	public Object getData() {
		return data;
	}
}
