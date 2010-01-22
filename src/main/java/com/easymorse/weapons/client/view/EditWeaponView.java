package com.easymorse.weapons.client.view;

import com.easymorse.weapons.client.model.Weapon;
import com.easymorse.weapons.client.presenter.EditWeaponPresenter;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;

public class EditWeaponView extends Composite implements
		EditWeaponPresenter.Display {

	private FlexTable detailsTable;
	private TextBox name;
	private TextArea description;
	private Button saveButton;
	private Button cancelButton;
	private Image image;

	public EditWeaponView() {
		VerticalPanel contentDetailsPanel = new VerticalPanel();
		contentDetailsPanel.setSpacing(4);
		contentDetailsPanel.setWidth("100%");
		initWidget(contentDetailsPanel);

		detailsTable = new FlexTable();
		detailsTable.setCellSpacing(0);
		detailsTable.setWidth("100%");
		detailsTable.addStyleName("contacts-ListContainer");
		detailsTable.getColumnFormatter().addStyleName(1, "add-contact-input");
		name = new TextBox();
		description = new TextArea();
		description.setWidth("12em");// TODO 权宜之计，要用formatter做细节布局处理。
		description.setVisibleLines(5);
		initDetailsTable();
		contentDetailsPanel.add(detailsTable);

		HorizontalPanel menuPanel = new HorizontalPanel();
		saveButton = new Button("保存");
		cancelButton = new Button("取消");
		menuPanel.add(saveButton);
		menuPanel.add(cancelButton);
		contentDetailsPanel.add(menuPanel);
	}

	private void initDetailsTable() {
		detailsTable.setWidget(0, 0, new Label("名称"));
		detailsTable.setWidget(0, 1, name);
		detailsTable.setWidget(1, 0, new Label("介绍"));
		detailsTable.setWidget(1, 1, description);
		detailsTable.setWidget(2, 0, new Label("图片"));
		// image=new Image();
		// detailsTable.setWidget(2, 1, image);
		// detailsTable.setWidget(3, 0, new Label("上传图片"));
		// detailsTable.setWidget(3, 1, new FileUpload());
		name.setFocus(true);
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public HasClickHandlers getCancelButton() {
		return this.cancelButton;
	}

	@Override
	public HasValue<String> getDescription() {
		return this.description;
	}

	@Override
	public HasValue<String> getName() {
		return this.name;
	}

	@Override
	public HasClickHandlers getSaveButton() {
		return this.saveButton;
	}

	@Override
	public void setData(final Weapon weapon) {
		this.name.setValue(weapon.getName());
		this.description.setValue(weapon.getDescription());
		this.image = new Image("getImage.do?id=" + weapon.getId() + "&time="
				+ System.currentTimeMillis());
		this.detailsTable.setWidget(2, 1, this.image);

		this.image.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				final DialogBox dialogBox = new DialogBox();
				dialogBox.setText("替换图片");
				dialogBox.setGlassEnabled(true);
				dialogBox.setAnimationEnabled(true);
				VerticalPanel dialogContents = new VerticalPanel();

				final FormPanel form = new FormPanel();
				form.setAction("upload.do");
				form.setEncoding(FormPanel.ENCODING_MULTIPART);
				form.setMethod(FormPanel.METHOD_POST);

				form.addSubmitCompleteHandler(new SubmitCompleteHandler() {
					@Override
					public void onSubmitComplete(SubmitCompleteEvent event) {
						dialogBox.setGlassEnabled(false);
						dialogBox.hide();
						setData(weapon);
					}
				});
				dialogContents.add(form);

				HorizontalPanel uploadPanel = new HorizontalPanel();
				form.add(uploadPanel);

				final FileUpload fileUpload = new FileUpload();
				uploadPanel.add(fileUpload);
				fileUpload.setName("file");
				
				Hidden hidden = new Hidden("id", weapon.getId());
				uploadPanel.add(hidden);

				Button sendButton = new Button("上传", new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						if (fileUpload.getFilename().isEmpty()) {
							Window.alert("请选择文件后上传");
						} else {
							form.submit();
						}
					}
				});
				uploadPanel.add(sendButton);

				Button closeButton = new Button("关闭", new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						dialogBox.setGlassEnabled(false);
						dialogBox.hide();
					}
				});
				dialogContents.add(closeButton);

				dialogContents.setSpacing(4);
				dialogBox.setWidget(dialogContents);
				dialogBox.show();
			}
		});
	}
}
