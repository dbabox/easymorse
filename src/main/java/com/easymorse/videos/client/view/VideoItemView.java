package com.easymorse.videos.client.view;

import com.easymorse.videos.client.model.VideoItem;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;

public class VideoItemView extends Composite {

	private VideoItem videoItem;

	private PushButton button;

	public PushButton getPlayButton() {
		return button;
	}

	public VideoItemView(final VideoItem videoItem) {
		this.videoItem = videoItem;

		HorizontalPanel panel = new HorizontalPanel();
		initWidget(panel);
		panel.setSpacing(5);

		VerticalPanel imagePanel = new VerticalPanel();
		panel.add(imagePanel);
		imagePanel.setVerticalAlignment(VerticalPanel.ALIGN_BOTTOM);

		Image image = new Image("1.jpg");
		button = new PushButton(image);
		button.setTitle("播放");
		imagePanel.add(button);

		VerticalPanel contentPanel = new VerticalPanel();
		contentPanel.setSpacing(3);
		contentPanel.setVerticalAlignment(VerticalPanel.ALIGN_TOP);
		contentPanel.add(new HTML("<h3>" + videoItem.getTitle() + "</h3>"));
		contentPanel.add(new HTML(videoItem.getContent()));
		panel.add(contentPanel);
	}

	public VideoItem getVideoItem() {
		return videoItem;
	}
}
