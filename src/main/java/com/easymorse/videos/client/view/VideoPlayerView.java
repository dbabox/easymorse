package com.easymorse.videos.client.view;

import com.bramosystems.oss.player.core.client.AbstractMediaPlayer;
import com.bramosystems.oss.player.core.client.LoadException;
import com.bramosystems.oss.player.core.client.PlayerUtil;
import com.bramosystems.oss.player.core.client.Plugin;
import com.bramosystems.oss.player.core.client.PluginNotFoundException;
import com.bramosystems.oss.player.core.client.PluginVersionException;
import com.bramosystems.oss.player.core.client.ui.FlashMediaPlayer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class VideoPlayerView extends Composite {

	private Button closeButton;

	public Button getCloseButton() {
		return closeButton;
	}

	public VideoPlayerView(String url) {
		VerticalPanel panel = new VerticalPanel();
		panel.setSpacing(5);
		initWidget(panel);

		AbstractMediaPlayer player = null;
		try {
			// create the player, specifing URL of media
			player = new FlashMediaPlayer(url);
			panel.add(player); // add player to panel.
		} catch (LoadException e) {
			// catch loading exception and alert user
			Window.alert("An error occured while loading");
		} catch (PluginVersionException e) {
			// required plugin version is not available,
			// alert user possibly providing a link to the plugin download page.
			panel.add(new HTML(".. some nice message telling the "
					+ "user to download plugin first .."));
		} catch (PluginNotFoundException e) {
			// catch PluginNotFoundException and display a friendly notice.
			panel.add(PlayerUtil.getMissingPluginNotice(Plugin.FlashPlayer));
		}

		closeButton = new Button("关闭");
		panel.add(closeButton);
	}
}
