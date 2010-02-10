package mis.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootPanel;

public class App implements EntryPoint {
	
	@Override
	public void onModuleLoad() {
		HandlerManager handlerManager = new HandlerManager(null);
		new AppController(handlerManager).go(RootPanel.get());
	}

}
