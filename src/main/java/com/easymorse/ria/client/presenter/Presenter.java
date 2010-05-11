package com.easymorse.ria.client.presenter;

import com.google.gwt.user.client.ui.HasWidgets;

/**
 * 封装对应视图的业务逻辑
 * 
 * @author marshal
 * 
 */
public interface Presenter {
	/**
	 * 对给出的视图对象进行逻辑处理。
	 * 
	 * @param container
	 */
	public abstract void go(final HasWidgets container);
}
