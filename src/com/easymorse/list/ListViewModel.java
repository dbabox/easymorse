package com.easymorse.list;

import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

import android.content.Context;

public class ListViewModel {
	// Activity对象
	Context context;

	// checkbox是否可见的标志位
	boolean checkItemVisible;

	// 存放选中的checkbox条目的图片列表下标
	Set<Long> checkedIds = new HashSet<Long>();

	/**
	 * 自定义的用于观察的对象类，当ListView中的元素监控到ui变化通知所有元素改变ui
	 * 
	 * @author marshal
	 * 
	 */
	class ListViewUIChangeObservable extends Observable {
		public void toolbarStatusChanged(boolean visible) {
			setChanged();
			notifyObservers(visible);
		}
	}

	/**
	 * listview行元素变化观察对象
	 * 
	 * @author marshal
	 * 
	 */
	class ListViewElementChangeObservable extends Observable {
		public void listViewNeedChange(ListViewNeedChangeData changeData) {
			setChanged();
			notifyObservers(changeData);
		}
	}

	ListViewUIChangeObservable listViewUIChangeObservable = new ListViewUIChangeObservable();

	ListViewElementChangeObservable elementChangeObservable = new ListViewElementChangeObservable();
}
