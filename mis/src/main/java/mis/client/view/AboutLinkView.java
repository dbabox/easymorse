package mis.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AboutLinkView extends Composite {

	private Image logo;

	private String aboutTitle;

	private String aboutDescription;

	public void setLogo(Image logo) {
		this.logo = logo;
	}

	public void setAboutTitle(String aboutTitle) {
		this.aboutTitle = aboutTitle;
	}

	public void setAboutDescription(String aboutDescription) {
		this.aboutDescription = aboutDescription;
	}

	public AboutLinkView() {
		HorizontalPanel panel = new HorizontalPanel();
		initWidget(panel);

		Anchor helpAnchor = new Anchor("帮助");
		panel.add(helpAnchor);

		panel.add(new HTML("&nbsp;|&nbsp;"));

		Anchor aboutAnchor = new Anchor("关于");
		panel.add(aboutAnchor);

		aboutAnchor.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final DialogBox box = new DialogBox();
				box.setText("关于");
				box.setGlassEnabled(true);
				box.setAnimationEnabled(true);

				HorizontalPanel panel = new HorizontalPanel();
				panel.setStyleName("Application-title");
				box.add(panel);

				if (logo == null) {
					logo = new Image("logo.gif");
				}

				panel.add(logo);

				VerticalPanel contentPanel = new VerticalPanel();
				panel.add(contentPanel);

				contentPanel
						.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
				contentPanel.setSpacing(5);

				if (aboutTitle == null) {
					aboutTitle = "EasyMorse管理信息系统";
				}

				contentPanel.add(new HTML("<h1>" + aboutTitle
						+ "</h1><h2>0.1.0 (build 2303)</h2>"));

				if (aboutDescription == null) {
					aboutDescription = "©2010 <a href=''>EasyMorse Inc.</a>";
				}

				contentPanel.add(new HTML(aboutDescription));

				HorizontalPanel buttonPanel = new HorizontalPanel();
				buttonPanel.setWidth("100%");
				contentPanel.add(buttonPanel);
				buttonPanel
						.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

				Button closeButton = new Button("关闭");
				buttonPanel.add(closeButton);
				closeButton.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						box.hide();
					}
				});

				box.center();
				box.show();
			}
		});
	}

}
