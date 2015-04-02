package com.billionwang.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.mapapi.search.busline.BusLineResult;
import com.baidu.mapapi.search.busline.BusLineSearch;
import com.baidu.mapapi.search.busline.BusLineSearchOption;
import com.baidu.mapapi.search.busline.OnGetBusLineSearchResultListener;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.billionwang.activity.StationListActivity;
import com.billionwang.utils.BusUtils;
import com.example.baidubus.R;

public class LineSearchFragment extends Fragment implements OnGetPoiSearchResultListener, OnGetBusLineSearchResultListener{
	private PoiSearch mSearch ;
	private BusLineSearch mBusLineSearch ;
	private BusLineResult route ;
	private Button btnSearchCar ;
	private String startstation;
	private String lastStation;
	private String firstClass;
	private String lastClass;
	private String price;
	private String busLineName;
	private String busLineNum;
	private ArrayList<String> stationArraylist;
	private List<String> busLineIDList ;
	
	private EditText carEditText;
	View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 view = inflater.inflate(R.layout.fragment_line_search, container,false);
				return view;
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		btnSearchCar = (Button) view.findViewById(R.id.btn_search_car);
		btnSearchCar.setOnClickListener(new BtnSearchCarClick());
		//之前写成carEditText = (EditText) view.findViewById(R.id.textView1);
		//没有textView1，carEditText是null，报错。
		carEditText = (EditText) view.findViewById(R.id.editText1);
		mSearch = PoiSearch.newInstance();
		mSearch.setOnGetPoiSearchResultListener(this);
		mBusLineSearch = BusLineSearch.newInstance();
		mBusLineSearch.setOnGetBusLineSearchResultListener(this);
		busLineIDList = new ArrayList<String>();
	}
	
	class BtnSearchCarClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			
			if((carEditText.getText().toString()).equals("")){
				Toast.makeText(getActivity().getBaseContext(), "线路名不能为空", 0).show();
				Log.d("onClick", "线路名不能为空");
				return;
			}
			busLineNum = carEditText.getText().toString();
			mSearch.searchInCity((new PoiCitySearchOption()).city("苏州")
					.keyword(carEditText.getText().toString()));
			
		}
    	
    }

	@Override
	public void onGetBusLineResult(BusLineResult result) {
		ArrayList<String> busLineStation = new ArrayList<String>();
		// TODO Auto-generated method stub
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			return;
		}
		route = result;
		Log.d("result", route.getBusLineName());
	
		for (int i = 0; i < result.getStations().size(); i++) {
			busLineStation.add(route.getStations().get(i).getTitle());
			Log.d("stationName", busLineStation.get(i));
		}
		stationArraylist = busLineStation;
		
		startstation = stationArraylist.get(0);
		lastStation = stationArraylist.get(stationArraylist.size()-1);
		firstClass = "首班" + BusUtils.addZeroBeforeTime(route.getStartTime());
		lastClass = "末班" +  BusUtils.addZeroBeforeTime(route.getEndTime());
		price = "票价2.0元";
		busLineName = route.getBusLineName();
		
		Intent intent = new Intent(getActivity(),StationListActivity.class);
		intent.putExtra("startstation", startstation);
		intent.putExtra("firstClass", firstClass);
		intent.putExtra("lastClass",lastClass);
		intent.putExtra("price", price);
		intent.putExtra("lastStation",lastStation);
		intent.putExtra("stationArraylist",stationArraylist);
		intent.putExtra("busLineName", busLineName);
		intent.putExtra("buLineNum", busLineNum);
		startActivity(intent);
		
	
	}
	
	
	
	@Override
	public void onGetPoiDetailResult(PoiDetailResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetPoiResult(PoiResult result) {
		// TODO Auto-generated method stub
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			
			return;
		}
		
		busLineIDList.clear();
		for (PoiInfo poi : result.getAllPoi()) {
			if (poi.type == PoiInfo.POITYPE.BUS_LINE
					|| poi.type == PoiInfo.POITYPE.SUBWAY_LINE) {
				
				busLineIDList.add(poi.uid);
			}
		}
		Log.d("busLineIDList", busLineIDList.size()+"");
		SearchNextBusline("苏州",busLineIDList.get(0));
	}
	
	
public void SearchNextBusline(String city,String uid) {
		
		mBusLineSearch.searchBusLine((new BusLineSearchOption()
				.city(city)).uid(uid));
	}

	
	
	
}
