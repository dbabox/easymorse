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

		Image image = new Image("1.jpg");
		button = new PushButton(image);
		button.setTitle("播放");
		panel.add(button);
		// image.addClickHandler(new ClickHandler() {
		// @Override
		// public void onClick(ClickEvent event) {
		// final DialogBox box = new DialogBox();
		// // box.setWidth("400");
		//
		// VerticalPanel panel = new VerticalPanel();
		// // ArrayList<String> items = new ArrayList<String>();
		// // items.add(new String(GWT.getHostPageBaseURL()
		// // + "media/big-buck-bunny.mp4"));
		// // items.add(new String(GWT.getHostPageBaseURL()
		// // + "media/big-buck-bunny.ogv"));
		// AbstractMediaPlayer player = null;
		// try {
		// player = new FlashMediaPlayer(
		// "http://marshal.easymorse.com/videos/test.mp4");
		// panel.add(player);
		// } catch (PluginNotFoundException e) {
		// e.printStackTrace();
		// } catch (LoadException e) {
		// e.printStackTrace();
		// } catch (PluginVersionException e) {
		// e.printStackTrace();
		// }
		//
		// box.setText("播放:" + videoItem.getTitle());
		// box.add(panel);
		//
		// Button button = new Button("关闭");
		// panel.add(button);
		// button.addClickHandler(new ClickHandler() {
		//
		// @Override
		// public void onClick(ClickEvent event) {
		// box.clear();
		// box.hide();
		// }
		// });
		//
		// box.setAnimationEnabled(true);
		// box.center();
		// box.show();
		// }
		// });

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.add(new HTML("<h3>" + videoItem.getTitle() + "</h3>"));
		verticalPanel.add(new HTML(videoItem.getContent()));
		panel.add(verticalPanel);
	}

	public VideoItem getVideoItem() {
		return videoItem;
	}
}
