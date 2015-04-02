package com.billionwang.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
import com.billionwang.utils.BusUtils;
import com.example.baidubus.R;

public class LineListActivity extends Activity implements OnGetPoiSearchResultListener, OnGetBusLineSearchResultListener{
	ListView listView1 = null;
	ArrayAdapter<String> myAdapter = null;
	ArrayList<String> arrayList = null;
	private PoiSearch mSearch =null;
	private BusLineSearch mBusLineSearch =null;
	private BusLineResult route =null;
	private List<String> busLineIDList ;
	private ArrayList<String> stationArraylist;
	private String startstation;
	private String lastStation;
	private String firstClass;
	private String lastClass;
	private String price;
	private String busLineName;
	private String busLineNum;
	private String stationName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_line_list);
		ActionBar actionBar = getActionBar();
		mSearch = PoiSearch.newInstance();
		mSearch.setOnGetPoiSearchResultListener(this);
		mBusLineSearch = BusLineSearch.newInstance();
		mBusLineSearch.setOnGetBusLineSearchResultListener(this);
		Intent intent = getIntent();
		busLineIDList = new ArrayList<String>();
		arrayList = intent.getStringArrayListExtra("stationLine");
		stationName = intent.getStringExtra("stationName");
		actionBar.setTitle(stationName+"("+ "共有"+ arrayList.size()+"趟车经过)");
		Log.d("arrayList", arrayList.size()+"");
		listView1 = (ListView) findViewById(R.id.listView1);
		myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
		listView1.setAdapter(myAdapter);
		listView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				busLineNum = arrayList.get(position);
				mSearch.searchInCity(new PoiCitySearchOption().city("苏州").keyword(busLineNum));
				Log.d("busLineNum", busLineNum);
			}
		});
	}
	@Override
	public void onGetBusLineResult(BusLineResult result) {
		// TODO Auto-generated method stub
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

		Intent intent = new Intent(LineListActivity.this,StationListActivity.class);
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
