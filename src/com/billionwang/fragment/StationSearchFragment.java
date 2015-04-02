package com.billionwang.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.PoiInfo.POITYPE;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.billionwang.activity.LineListActivity;
import com.example.baidubus.R;

public class StationSearchFragment extends Fragment{
	View view;
	EditText editText;
	Button buttonSearch;
	PoiSearch mPoiSearch ;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 view = inflater.inflate(R.layout.fragment_station_search, container,false);
		
		return view;
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		//contentWebView = (WebView) view.findViewById(R.id.webView1);
		editText = (EditText) view.findViewById(R.id.editText1);
		//listView = (ListView)view.findViewById(R.id.listView1);
		buttonSearch = (Button) view.findViewById(R.id.button1);
		buttonSearch.setOnClickListener(new ButtonClick());
		mPoiSearch = PoiSearch.newInstance();
		
		mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
			
			@Override
			public void onGetPoiResult(PoiResult result) {
				ArrayList<String> arrayListLine = new ArrayList<String>();
				// TODO Auto-generated method stub
				if(result == null
						|| result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
					Toast.makeText(getActivity().getBaseContext(), "未找到结果", Toast.LENGTH_LONG)
					.show();
					return;
				}
				if (result.error == SearchResult.ERRORNO.NO_ERROR) {
					List<PoiInfo> info = result.getAllPoi();
					for(PoiInfo poiInfo: info){
						if(poiInfo.type == POITYPE.BUS_STATION){
							String[] lineArray = poiInfo.address.split(";");
							for(int i =0;i<lineArray.length;i++){
								arrayListLine.add(lineArray[i]);
							}
							Intent intent = new Intent(getActivity().getBaseContext(),LineListActivity.class);
							intent.putExtra("stationLine", arrayListLine);
							intent.putExtra("stationName", poiInfo.name);
							startActivity(intent);
							return;
						}
					}
					Toast.makeText(getActivity().getBaseContext(), "请输入精确地址", Toast.LENGTH_LONG).show();
					
					
				}
				if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

					// 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
					String strInfo = "在";
					for (CityInfo cityInfo : result.getSuggestCityList()) {
						strInfo += cityInfo.city;
						strInfo += ",";
					}
					strInfo += "找到结果";
					Toast.makeText(getActivity().getBaseContext(), strInfo, Toast.LENGTH_LONG)
							.show();
				}
			}
			
			@Override
			public void onGetPoiDetailResult(PoiDetailResult result) {
				// TODO Auto-generated method stub
				if (result.error != SearchResult.ERRORNO.NO_ERROR) {
					Toast.makeText(getActivity().getBaseContext(), "抱歉，未找到结果", Toast.LENGTH_SHORT)
							.show();
				}
				else {
					
					Toast.makeText(getActivity().getBaseContext() ,result.getName() + ": " + result.getAddress(), Toast.LENGTH_SHORT)
					.show();
				}
			}
		});
	 	
	}

	private class ButtonClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.button1:
				//contentWebView.loadUrl("javascript:inputStation(" + "'兰凤寺'" + ")");
				PoiCitySearchOption option = new PoiCitySearchOption();
				option.city("苏州")
				.keyword(editText.getText().toString());
				mPoiSearch.searchInCity(option);
				break;

			default:
				break;
			}
		}
		
	}
	
	

}
