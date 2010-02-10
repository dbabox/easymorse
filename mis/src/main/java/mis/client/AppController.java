package mis.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mis.client.presenter.Presenter;
import mis.client.view.AboutLinkView;
import mis.client.view.ApplicationView;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

public class AppController implements Presenter {

	private HandlerManager handlerManager;

	private ApplicationView view;

	public AppController(HandlerManager handlerManager) {
		this.handlerManager = handlerManager;
	}

	@Override
	public void go(HasWidgets container) {
		view = new ApplicationView();
		AboutLinkView linkView = new AboutLinkView();
		view.setLink(linkView);
		container.add(view);

		this.bind();
	}

	private void bind() {
view.addSelectionHandler(new SelectionHandler<TreeItem>() {

	@Override
	public void onSelection(SelectionEvent<TreeItem> event) {
		TreeItem item = event.getSelectedItem();

		if (item.getParentItem() != null) {
			Tree tree = item.getTree();
			for (Iterator<TreeItem> iterator = tree.treeItemIterator(); iterator
					.hasNext();) {
				TreeItem i = iterator.next();
				if (i.getParentItem() == null) {
					if (i.getChildIndex(item) == -1) {
						i.setState(false, false);
					}
				}
			}
		}
	}
});
	}
}
