package com.easymorse.videos.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class BrowseVideoItemsEvent extends
		GwtEvent<BrowseVideoItemsEventHandler> {

	public static Type<BrowseVideoItemsEventHandler> TYPE = new Type<BrowseVideoItemsEventHandler>();
	
	private int pageNo;
	
	public BrowseVideoItemsEvent(int pageNo) {
		this.pageNo=pageNo;
	}
	
	public int getPageNo() {
		return pageNo;
	}
	
	@Override
	protected void dispatch(BrowseVideoItemsEventHandler handler) {
		handler.onBrowseVideoItem(this);
	}

	@Override
	public Type<BrowseVideoItemsEventHandler> getAssociatedType() {
		return TYPE;
	}

}
