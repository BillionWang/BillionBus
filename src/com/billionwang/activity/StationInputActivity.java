package com.billionwang.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionResult.SuggestionInfo;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.billionwang.adapter.MyCityAddressAdapter;
import com.billionwang.entity.CityAddress;
import com.example.baidubus.R;

public class StationInputActivity extends Activity implements OnGetSuggestionResultListener{
	Button btnConfirm = null;
	ListView lvCityAddress = null;;
	EditText etPosition = null;
	ImageView imgBack = null;
	ArrayList<CityAddress> cityAddressesList = null;
	MyCityAddressAdapter cityAddressAdapter = null;
	String hintString;
	SuggestionSearch suggestionSearch = null;
	ProgressBar progressBar = null;
	boolean itemClick = false; //区分itemClick和editTextClick
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_station_input);
	    hintString = getIntent().getStringExtra("position");
		initView();
		Log.d("onCreate", "onCreate");
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d("onStart", "onStart");
	}
	
	private void initView(){
		btnConfirm = (Button) findViewById(R.id.btnConfirm);
		lvCityAddress = (ListView) findViewById(R.id.listView1);
		etPosition = (EditText) findViewById(R.id.etPosition);
		etPosition.setHint(hintString);
		imgBack = (ImageView) findViewById(R.id.imgBack);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		cityAddressesList = new ArrayList<CityAddress>();
		cityAddressAdapter = new MyCityAddressAdapter(this, cityAddressesList);
		lvCityAddress.setAdapter(cityAddressAdapter);
		suggestionSearch = SuggestionSearch.newInstance();
		suggestionSearch.setOnGetSuggestionResultListener(this);
		imgBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent=  new Intent(StationInputActivity.this,LineRouteActivity.class);
				startActivity(intent);
				
			}
		});
		
		btnConfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(etPosition.getText().length()<1){
					Toast.makeText(getBaseContext(), "起点不能为空", Toast.LENGTH_SHORT).show();
				}
				else {
					Log.d("btnConfirm", "click");
					Intent intent = new Intent();
					intent.putExtra("data_return", etPosition.getText().toString());
					setResult(RESULT_OK,intent);
					Log.d("data_return", intent.getStringExtra("data_return"));
					finish();		
				
				}
			}
		});
		
		etPosition.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if(!itemClick){
					
					if(s.length()>0){
						
						Log.d("onTextChanged", "onTextChanged");
						Log.d("length", s.toString());
						suggestionSearch.requestSuggestion(new SuggestionSearchOption()
						.keyword(s.toString()).city("苏州"));
						progressBar.setVisibility(View.VISIBLE);
					}
				}
				else {
					itemClick = false;
				}
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		lvCityAddress.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				itemClick = true; //先设置itemClick，顺序反了就不管用了
				etPosition.setText(cityAddressesList.get(position).getCityAddressName());
				etPosition.setSelection(etPosition.getText().length());
			}
		});
		
	}

	
	@Override
	public void onGetSuggestionResult(SuggestionResult result) {
		// TODO Auto-generated method stub
		cityAddressesList.clear();
		List<SuggestionInfo> suggestionInfos = new ArrayList<SuggestionResult.SuggestionInfo>();
		if(result.error == SearchResult.ERRORNO.NO_ERROR){
			suggestionInfos = result.getAllSuggestions();
			if((null != suggestionInfos) && (suggestionInfos.size()>0) ){
				for (SuggestionInfo suggestionInfo : suggestionInfos) {
					CityAddress cityAddress = new CityAddress(suggestionInfo.key, suggestionInfo.city+suggestionInfo.district);
					cityAddressesList.add(cityAddress);
				}
				cityAddressAdapter.notifyDataSetChanged();
			}
			else {
				Toast.makeText(getBaseContext(), "没有合适的地址",Toast.LENGTH_SHORT).show();
			}
			progressBar.setVisibility(View.GONE);
			
		}
		else {
			Toast.makeText(this, "没有合适的地址", Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		suggestionSearch.destroy();
	}
	
	
}
