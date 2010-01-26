package com.easymorse.videos.client;

import com.bramosystems.oss.player.core.client.AbstractMediaPlayer;
import com.bramosystems.oss.player.core.client.LoadException;
import com.bramosystems.oss.player.core.client.PluginNotFoundException;
import com.bramosystems.oss.player.core.client.PluginVersionException;
import com.bramosystems.oss.player.core.client.ui.FlashMediaPlayer;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class Videos implements EntryPoint {

	@Override
	public void onModuleLoad() {
		SimplePanel panel = new SimplePanel();
		AbstractMediaPlayer player = null;

		try {
			player = new FlashMediaPlayer(
					"http://marshal.easymorse.com/videos/test.mp4");
			panel.setWidget(player);
		} catch (PluginNotFoundException e) {
			e.printStackTrace();
		} catch (PluginVersionException e) {
			e.printStackTrace();
		} catch (LoadException e) {
			e.printStackTrace();
		}
		
		RootPanel.get().add(panel);

	}

}
