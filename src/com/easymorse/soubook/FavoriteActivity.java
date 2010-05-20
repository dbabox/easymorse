package com.easymorse.soubook;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

public class FavoriteActivity extends Activity {

	private WebView resultWeb;

	private Button returnButton;

	private Button cleanButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favorite_list);

		this.returnButton = (Button) this.findViewById(R.id.returnButton);
		this.returnButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		this.cleanButton = (Button) this.findViewById(R.id.cleanButton);
		this.cleanButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new Builder(FavoriteActivity.this);
				builder.setTitle("警告");
				builder.setMessage("是否真的要删除所有收藏夹条目?");
				builder.setPositiveButton("确认",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								BookInfoDao.getInstance().deleteAll();
								new Handler().post(new Runnable() {
									
									@Override
									public void run() {
										resultWeb.loadUrl("javascript:listFavorite()");
									}
								});
								dialog.dismiss();
							}

						});
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
					
				});
				
				builder.create().show();
			}
		});

		this.resultWeb = (WebView) this.findViewById(R.id.favoriteWeb);
		this.resultWeb.getSettings().setSupportZoom(false);
		this.resultWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(
				true);
		this.resultWeb.getSettings().setJavaScriptEnabled(true);

		this.resultWeb.loadUrl("file:///android_asset/favorite_list.html");

		this.resultWeb.addJavascriptInterface(new Object() {
			public String getFavoriteResult() {
				return BookInfoDao.getInstance().list().toString();
			}
		}, "favoriteControl");
	}
}
