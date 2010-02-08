package com.easymorse.videos.client.view;

import com.bramosystems.oss.player.core.client.AbstractMediaPlayer;
import com.bramosystems.oss.player.core.client.LoadException;
import com.bramosystems.oss.player.core.client.PlayerUtil;
import com.bramosystems.oss.player.core.client.Plugin;
import com.bramosystems.oss.player.core.client.PluginNotFoundException;
import com.bramosystems.oss.player.core.client.ui.NativePlayer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
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

		SimplePanel simplePanel = new SimplePanel();
		panel.add(simplePanel);

		AbstractMediaPlayer player = getPlayer(url, panel);
		simplePanel.add(player);
		// try {
		// // create the player, specifing URL of media
		// player = new FlashMediaPlayer(url);
		// panel.add(player); // add player to panel.
		// } catch (LoadException e) {
		// // catch loading exception and alert user
		// Window.alert("An error occured while loading");
		// } catch (PluginVersionException e) {
		// // required plugin version is not available,
		// // alert user possibly providing a link to the plugin download page.
		// panel.add(new HTML(".. some nice message telling the "
		// + "user to download plugin first .."));
		// } catch (PluginNotFoundException e) {
		// // catch PluginNotFoundException and display a friendly notice.
		// panel.add(PlayerUtil.getMissingPluginNotice(Plugin.FlashPlayer));
		// }

		closeButton = new Button("关闭");
		panel.add(closeButton);
	}

	private AbstractMediaPlayer getPlayer(String url, Panel panel) {
		AbstractMediaPlayer player = null;

		// try {
		// Window.alert(url);
		// player = new WinMediaPlayer(url);
		// } catch (Exception e0) {
		try {
			// create the player, specifing URL of media
			String[] sizeInfo = url.substring(url.indexOf("=") + 1).split("x");
			player = new NativePlayer(url, true, sizeInfo[1] + "px",
					sizeInfo[0] + "px");
			// NativePlayer p=(NativePlayer) player;
			// Window.alert(">>"+p.getVideoWidth());
			// player.setWidth("100%");
			panel.add(player); // add player to panel.
		} catch (LoadException e) {
			// catch loading exception and alert user
			Window.alert("An error occured while loading");
			// } catch (PluginVersionException e) {
			// // required plugin version is not available,
			// // alert user possibly providing a link to the plugin download
			// // page.
			// panel.add(new HTML(".. some nice message telling the "
			// + "user to download plugin first .."));
		} catch (PluginNotFoundException e) {
			// catch PluginNotFoundException and display a friendly notice.
			panel.add(PlayerUtil.getMissingPluginNotice(Plugin.Native));
		}
		// }

		return player;
	}

}
