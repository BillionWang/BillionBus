package com.billionwang.activity;

import com.example.baidubus.R;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

public class AboutAppActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_app);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
}
