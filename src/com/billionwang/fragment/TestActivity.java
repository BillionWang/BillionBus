package com.billionwang.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.baidubus.R;

public class TestActivity extends Activity{
	Button btn;
	EditText etEditText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		etEditText = (EditText) findViewById(R.id.editText1);
		btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String return_dataString = etEditText.getText().toString();
				Intent intent = new Intent();
				intent.putExtra("return_data", return_dataString);
				setResult(RESULT_OK, intent);
				String name = getCallingActivity().getClassName();
				Log.d("name", name);
				finish();
			}
		});
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d("onStart", "onStart");
		
	}
	
}
