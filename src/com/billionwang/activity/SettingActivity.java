package com.billionwang.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.baidubus.R;

public class SettingActivity extends Activity{
	private View changeCity ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		changeCity = findViewById(R.id.more_page_row7);
		changeCity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SettingActivity.this,AboutAppActivity.class);
				startActivity(intent);
			}
		});
	}
}
