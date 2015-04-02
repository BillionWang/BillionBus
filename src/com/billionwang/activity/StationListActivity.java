package com.billionwang.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.search.busline.BusLineResult;
import com.baidu.mapapi.search.busline.BusLineSearch;
import com.baidu.mapapi.search.busline.BusLineSearchOption;
import com.baidu.mapapi.search.busline.OnGetBusLineSearchResultListener;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.PoiInfo.POITYPE;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.billionwang.adapter.MyStationAdapter;
import com.billionwang.utils.BusUtils;
import com.example.baidubus.R;

public class StationListActivity extends Activity implements OnGetPoiSearchResultListener, OnGetBusLineSearchResultListener{
	
	private PoiSearch mSearch ;
	private BusLineSearch mBusLineSearch ;
	private BusLineResult route ;
	private TextView tvStartStation;
	private TextView tvFirstClass;
	private TextView tvLastClass;
	private TextView tvPrice;
	private TextView tvTargetStation;
	private ListView lView;
	private MyStationAdapter myAdapter;
	private ArrayList<String> stationList;
	private String lineNum;
	private List<String> busLineIDList ;
	private Boolean flag; //根据flag确定返程
	ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_station_list);
		actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);  
		
		Intent intent = getIntent();
		stationList = intent.getStringArrayListExtra("stationArraylist");
		initView();
		tvStartStation.setText(intent.getStringExtra("startstation"));
		tvFirstClass.setText(intent.getStringExtra("firstClass"));
		tvLastClass.setText(intent.getStringExtra("lastClass"));
		tvPrice.setText(intent.getStringExtra("price"));
		tvTargetStation.setText(intent.getStringExtra("lastStation"));
		lView.setAdapter(myAdapter);
		lineNum = intent.getStringExtra("buLineNum");
		actionBar.setTitle(intent.getStringExtra("busLineName"));
		lView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				PoiCitySearchOption option = new PoiCitySearchOption();
				option.city("苏州")
				.keyword(stationList.get(position));
				mSearch.searchInCity(option);
				
			}
		});
		
		
	}
	
	private void initView(){
		tvStartStation = (TextView) findViewById(R.id.tv_station);
		tvFirstClass = (TextView) findViewById(R.id.tv_first_class);
		tvLastClass  = (TextView) findViewById(R.id.tv_last_class);
		tvPrice = (TextView) findViewById(R.id.tv_price);
		tvTargetStation = (TextView) findViewById(R.id.tv_station2);
		lView = (ListView) findViewById(R.id.listView1);
		myAdapter = new MyStationAdapter(getBaseContext(), stationList);
		mSearch = PoiSearch.newInstance();
		mSearch.setOnGetPoiSearchResultListener(this);
		mBusLineSearch = BusLineSearch.newInstance();
		mBusLineSearch.setOnGetBusLineSearchResultListener(this);
		flag = true;
		busLineIDList = new ArrayList<String>();
	}
	
	  @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.line_search, menu);
	        return true;
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle action bar item clicks here. The action bar will
	        // automatically handle clicks on the Home/Up button, so long
	        // as you specify a parent activity in AndroidManifest.xml.
	        int id = item.getItemId();
	        if (id == R.id.return_line) {
	        	getReturn();
	            return true;
	        }
	        return super.onOptionsItemSelected(item);
	    }
	
	    //点击返程调用该方法
	private void getReturn(){
		if(lineNum.equals("")){
			return;
		}
		mSearch.searchInCity((new PoiCitySearchOption()).city("苏州")
				.keyword(lineNum));
		
	}

	@Override
	public void onGetBusLineResult(BusLineResult result) {
		// TODO Auto-generated method stub
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			return;
		}
		route = result;
		ArrayList<String> busLineStation = new ArrayList<String>();
		for (int i = 0; i < result.getStations().size(); i++) {
			busLineStation.add(route.getStations().get(i).getTitle());
			Log.d("stationName", busLineStation.get(i));
		}
		stationList = busLineStation;
		tvStartStation.setText(stationList.get(0));
		tvTargetStation.setText(stationList.get(stationList.size()-1));
		tvFirstClass.setText("首班" + BusUtils.addZeroBeforeTime(route.getStartTime()));
		tvLastClass.setText("末班" +  BusUtils.addZeroBeforeTime(route.getEndTime()));
		actionBar.setTitle(route.getBusLineName());
		myAdapter.notifyDataSetChanged();
	}

	@Override
	public void onGetPoiDetailResult(PoiDetailResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetPoiResult(PoiResult result) {
		ArrayList<String> arrayListLine = new ArrayList<String>();
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
			else if(poi.type == POITYPE.BUS_STATION){
				String[] lineArray = poi.address.split(";");
				for(int i=0;i<lineArray.length;i++){
					arrayListLine.add(lineArray[i]);
				}
				Intent intent = new Intent(StationListActivity.this,LineListActivity.class);
				intent.putExtra("stationLine", arrayListLine);
				intent.putExtra("stationName", poi.name);
				startActivity(intent);
				return;
			}
			
			
		}
		if(busLineIDList.size()<1){
			return;
		}
		else {
			if(busLineIDList.size()<2){
				Toast.makeText(getBaseContext(), "对不起没有对应的返程", 0).show();
				return;
			}
			
		}
		Log.d("busLineIDList", busLineIDList.size()+"");
		if(flag){
			flag = false;
			SearchNextBusline("苏州",busLineIDList.get(0));
		}
		else {
			flag = true;
			SearchNextBusline("苏州",busLineIDList.get(1));
		}
		
		
	}
	
public void SearchNextBusline(String city,String uid) {
		
		mBusLineSearch.searchBusLine((new BusLineSearchOption()
				.city(city)).uid(uid));
	}
	
}
