package com.easymorse.ria.client;

import com.easymorse.ria.client.presenter.Presenter;
import com.easymorse.ria.client.view.HelloworldView;
import com.easymorse.ria.client.view.MainLayoutView;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;

/**
 * 应用程序级的控制器。<br />
 * 
 * 这里处理的逻辑，是应用程序级别的，或者是不在具体Presenter的业务逻辑。比如窗口大小的改变处理，地址栏变化（历史）的处理。<br />
 * 
 * 应用程序级的控制器也是一个Presenter。
 * 
 * @author marshal
 * 
 */
public class ApplicationController implements ValueChangeHandler<String>,Presenter {
	private HandlerManager handlerManager;

	public ApplicationController(HandlerManager handlerManager) {
		this.handlerManager = handlerManager;
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {

	}

	@Override
	public void go(HasWidgets container) {
//		HelloworldView view=new HelloworldView("zhangsan");
//		container.add(view);
		container.add(new MainLayoutView("--"));
		
	}
}
