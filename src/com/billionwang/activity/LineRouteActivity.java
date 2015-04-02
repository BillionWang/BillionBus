package com.billionwang.activity;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRouteLine.TransitStep;
import com.baidu.mapapi.search.route.TransitRouteLine.TransitStep.TransitRouteStepType;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.billionwang.adapter.TransiRouteAdapter;
import com.billionwang.application.MyApplication;
import com.billionwang.entity.TransitRouteLineInfo;
import com.billionwang.utils.BusUtils;
import com.example.baidubus.R;

public class LineRouteActivity extends Activity{

	private TextView tvStart;
	private TextView tvEnd ;
	private TextView tvStartTime ;
	private TextView tvLocation;
	private ImageView imgLocation;
	private LocationClient mLocationClient;
	private ListView listViewInfo ;
	private View lineBottomView;
	private static final int START_POSITION=1;
	private static final int END_POSITION = 2;
	private RoutePlanSearch routePlanSearch;
	private ArrayList<TransitRouteLineInfo> transitRouteLineInfo = null; 
	private TransiRouteAdapter transiRouteAdapter;
	private ProgressBar progressBar;
	private PlanNode fromNode;
	private PlanNode toNode;
	private SharedPreferences pref;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_line_route);
		initView();
		
	}

	private void initView(){
		pref = getSharedPreferences("data", MODE_PRIVATE);
		tvStart = (TextView) findViewById(R.id.textViewStart);
		tvEnd   =  (TextView) findViewById(R.id.textViewEnd);
		tvStartTime = (TextView) findViewById(R.id.tvStartTime);
		tvLocation = (TextView) findViewById(R.id.tvLocation);
		lineBottomView = findViewById(R.id.linearLayoutBottom);
		imgLocation = (ImageView) findViewById(R.id.imgLocation);
		((MyApplication)getApplication()).mLocationResult = tvLocation;
		((MyApplication)getApplication()).mImageView = imgLocation;
		//ll = ((MyApplication)getApplication()).mLl; 
		
		mLocationClient = ((MyApplication)getApplication()).mLocationClient;
		initLocation();
		listViewInfo = (ListView) findViewById(R.id.listView1);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		transitRouteLineInfo = new ArrayList<TransitRouteLineInfo>();
		transiRouteAdapter = new TransiRouteAdapter(this, transitRouteLineInfo);
		listViewInfo.setAdapter(transiRouteAdapter);
		mLocationClient.start();
		imgLocation.setImageDrawable(getResources().getDrawable(R.drawable.icon_location_loading));
		lineBottomView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mLocationClient.start();
				imgLocation.setImageDrawable(getResources().getDrawable(R.drawable.icon_location_loading));
			}
		});
	
		
		tvStart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LineRouteActivity.this,StationInputActivity.class);
				intent.putExtra("position", "输入起点");
				startActivityForResult(intent, START_POSITION);

			}
		});

		tvEnd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LineRouteActivity.this,StationInputActivity.class);
				intent.putExtra("position", "输入终点");
				startActivityForResult(intent, END_POSITION);

			}
		});


		routePlanSearch = RoutePlanSearch.newInstance();
		routePlanSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {

			@Override
			public void onGetWalkingRouteResult(WalkingRouteResult arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onGetTransitRouteResult(TransitRouteResult result) {
				// TODO Auto-generated method stub
				
				if(result == null || result.error != SearchResult.ERRORNO.NO_ERROR){
					Toast.makeText(getBaseContext(),"抱歉，未找到结果" , Toast.LENGTH_SHORT).show();
					progressBar.setVisibility(View.GONE);
					return;
				}
				if(result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR ){
					progressBar.setVisibility(View.GONE);
					return;
				}
				if(result.error == SearchResult.ERRORNO.NO_ERROR){
					progressBar.setVisibility(View.GONE);
					listViewInfo.setVisibility(View.VISIBLE);
					transitRouteLineInfo.clear();
					TransitRouteLineInfo info = null;
					List<TransitRouteLine> mRouteLines =  result.getRouteLines();
					List<TransitStep> mTransitSteps = null;
					Calendar c = Calendar.getInstance();
					c.setTime(new Date());

					for (TransitRouteLine transitRouteLine : mRouteLines) {
						ArrayList<String> lineRoute = new ArrayList<String>();

						c.add(Calendar.SECOND, transitRouteLine.getDuration());
						String distanceStr = BusUtils.getKm(transitRouteLine.getDistance());
						String durationStr = BusUtils.getHour(transitRouteLine.getDuration());
						Log.d("duration", transitRouteLine.getDuration()+"");
						String arrivedTimeStr = BusUtils.addZeroBeforeTime(c.getTime())+"到达";
						int sumWalkingLength = 0;
						mTransitSteps = transitRouteLine.getAllStep();
						for (TransitStep transitStep : mTransitSteps) {
							Log.d("routeline", transitStep.getInstructions()+transitStep.getStepType().name()+transitStep.getDistance());
							if((transitStep.getStepType() == TransitRouteStepType.BUSLINE) 
									|| transitStep.getStepType() == TransitRouteStepType.SUBWAY){
								lineRoute.add(transitStep.getInstructions().split(",经过")[0]);
							}
							if(transitStep.getStepType() == TransitRouteStepType.WAKLING){
								transitStep.getDistance();
								sumWalkingLength+= transitStep.getDistance();
							}
						}
						String sumWalkingLengthStr = "步行" + String.valueOf(sumWalkingLength)+"米";
						String transitLineStr = BusUtils.getTransiLine(lineRoute);
						info = new TransitRouteLineInfo(transitLineStr, arrivedTimeStr, durationStr, distanceStr, sumWalkingLengthStr);
						transitRouteLineInfo.add(info);
					}
					
					transiRouteAdapter.notifyDataSetChanged();
				}

			}

			@Override
			public void onGetDrivingRouteResult(DrivingRouteResult arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		String position = "";
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
			position = data.getStringExtra("data_return");
			switch (requestCode) {
			case START_POSITION:
				tvStart.setText(position);
				if(tvEnd.getText().toString().equals("")){
					Toast.makeText(this, "请输入终点", Toast.LENGTH_SHORT).show();
				}
				if(!tvEnd.getText().toString().equals("")){
					tvStartTime.setVisibility(View.VISIBLE);
					tvStartTime.setText(BusUtils.getStartTime());
				    fromNode = PlanNode.withCityNameAndPlaceName("苏州", tvStart.getText().toString());
					if(tvStart.getText().toString().equals("我的位置")){
						 fromNode = PlanNode.withCityNameAndPlaceName("苏州", tvLocation.getText().toString());
						 Double latitude = Double.parseDouble(pref.getString("latitude", "31.356236"));
						 Double longitude = Double.parseDouble(pref.getString("longitude", "120.413823"));
						 LatLng ll = new LatLng(latitude, longitude);
						 fromNode = PlanNode.withLocation(ll);
					}
					Log.d("tvStart", tvStart.getText().toString());
				     toNode = PlanNode.withCityNameAndPlaceName("苏州", tvEnd.getText().toString());
					TransitRoutePlanOption option = new TransitRoutePlanOption();
					option.from(fromNode)
					.to(toNode)
					.city("苏州");
					routePlanSearch.transitSearch(option);
					progressBar.setVisibility(View.VISIBLE);
					listViewInfo.setVisibility(View.GONE);
				}
				break;
			case END_POSITION:
				tvEnd.setText(position);
				if(!tvStart.getText().toString().equals("")){
					tvStartTime.setVisibility(View.VISIBLE);
					tvStartTime.setText(BusUtils.getStartTime());
					
				    fromNode = PlanNode.withCityNameAndPlaceName("苏州", tvStart.getText().toString());
					if(tvStart.getText().toString().equals("我的位置")){
						 fromNode = PlanNode.withCityNameAndPlaceName("苏州", tvLocation.getText().toString());
						 Double latitude = Double.parseDouble(pref.getString("latitude", "31.356236"));
						 Double longitude = Double.parseDouble(pref.getString("longitude", "120.413823"));
						 LatLng ll = new LatLng(latitude, longitude);
						 fromNode = PlanNode.withLocation(ll);
					}
					Log.d("tvStart", tvStart.getText().toString());
				     toNode = PlanNode.withCityNameAndPlaceName("苏州", tvEnd.getText().toString());
					TransitRoutePlanOption option = new TransitRoutePlanOption();
					option.from(fromNode)
					.to(toNode)
					.city("苏州");
					routePlanSearch.transitSearch(option);
					progressBar.setVisibility(View.VISIBLE);
					listViewInfo.setVisibility(View.GONE);
				}

			default:
				break;
			}
		}
	}
	
	private void initLocation(){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);
		option.setCoorType("gcj02");
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}
	

}
