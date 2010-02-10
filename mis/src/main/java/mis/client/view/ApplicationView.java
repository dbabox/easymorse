package mis.client.view;

import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

public class ApplicationView extends Composite implements ResizeHandler,HasSelectionHandlers<TreeItem>  {

	private FlexTable topPanel;

	private HorizontalPanel linksPanel;

	private FlowPanel panel;

	private HorizontalPanel titlePanel;

	private HorizontalPanel mainPanel;

	private Tree mainMenu;

	public Tree getMainMenu() {
		return mainMenu;
	}

	public ApplicationView() {
		panel = new FlowPanel();
		initWidget(panel);

		createTopPanel();
		createMainPanel();

		// 设置窗口大小
		setSize();
		Window.addResizeHandler(this);
	}

	private void createMainPanel() {
		// 设置主菜单和主要内容部分的panel
		mainPanel = new HorizontalPanel();
		mainPanel.addStyleName("aaaa");
		mainPanel.setWidth("100%");
		mainPanel.setSpacing(0);
		mainPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		panel.add(mainPanel);

		// 设置菜单
		mainMenu = new Tree();
		mainMenu.setAnimationEnabled(true);
		mainMenu.addStyleName("Application-menu");
		mainPanel.add(mainMenu);

		TreeItem item = mainMenu.addItem("系统管理");
		item.addItem("数据库");
		item.addItem("日志").addItem("系统日志");

		item = mainMenu.addItem("用户管理");
		item.addItem("修改当前用户密码");

	}

	private void createTopPanel() {
		topPanel = new FlexTable();
		topPanel.setStyleName("Application-top");
		topPanel.setWidth("100%");
		topPanel.setCellPadding(0);
		topPanel.setCellSpacing(0);
		panel.add(topPanel);
		FlexCellFormatter formatter = topPanel.getFlexCellFormatter();

		// 设置标题部分
		titlePanel = new HorizontalPanel();
		titlePanel.setStyleName("Application-title");
		titlePanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		titlePanel.add(new Image("software_logo.png"));
		titlePanel.add(new HTML(
				"<h1>EasyMorse管理信息系统</h1><h2>用于MIS系统开发的初始版本</h2>"));
		topPanel.setWidget(1, 0, titlePanel);

		// 设置本地化选项
		VerticalPanel optionPanel = new VerticalPanel();
		optionPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		optionPanel.setWidth("100%");
		topPanel.setWidget(1, 1, optionPanel);
		formatter.setStyleName(1, 1, "Application-options");

		HorizontalPanel optionLanguagePanel = new HorizontalPanel();
		optionPanel.add(optionLanguagePanel);

		ListBox listBox = new ListBox();
		listBox.addItem("中文", "zh_CN");
		listBox.addItem("English", "en_US");
		listBox.setWidth("10em");

		optionLanguagePanel.add(new Image("locale.png"));
		optionLanguagePanel.add(new HTML("&nbsp;"));
		optionLanguagePanel.add(listBox);

		topPanel.getRowFormatter().setVerticalAlign(0,
				HasVerticalAlignment.ALIGN_TOP);
		topPanel.getRowFormatter().setVerticalAlign(1,
				HasVerticalAlignment.ALIGN_TOP);

		optionPanel.add(new Anchor("登录"));

	}

	public void setLink(Widget link) {
		// link.setStyleName("Application-links");
		topPanel.getFlexCellFormatter().setStyleName(0, 0, "Application-links");
		topPanel.setWidget(0, 0, link);
		topPanel.getFlexCellFormatter().setColSpan(0, 0, 2);
		topPanel.getFlexCellFormatter().setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_RIGHT);
	}

	public void setLogo(Image image) {

	}

	public void setTitle(String title) {

	}

	public void setSubTitle(String subTitle) {

	}

	@Override
	public void onResize(ResizeEvent event) {
		setSize();
	}

	protected void setSize() {
		this.setWidth("100%");
		this.setHeight("100%");
	}
	
	@Override
	public HandlerRegistration addSelectionHandler(
			SelectionHandler<TreeItem> handler) {
		return mainMenu.addSelectionHandler(handler);
	}

}
