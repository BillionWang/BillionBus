package com.billionwang.fragment;

import java.util.ArrayList;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.VehicleInfo;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRouteLine.TransitStep;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.search.route.TransitRoutePlanOption.TransitPolicy;
import com.billionwang.activity.LineRouteActivity;
import com.example.baidubus.R;

public class LineRouteFragment extends Fragment{
	private View view = null;
	private EditText editStart = null;
	private EditText editEnd   = null;
	private Button btnSearch = null;
	private static final int START_POSITION=1;
	private static final int END_POSITION = 2;
	private RoutePlanSearch mRoutePlanSearch = null;
	private List<TransitRouteLine> mTransitRouteLines = null; 
	private ImageView imageView = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 view = inflater.inflate(R.layout.fragment_line_route, container,false);
				return view;
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		imageView = (ImageView) view.findViewById(R.id.imageView1);
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity().getBaseContext(),LineRouteActivity.class);
				startActivity(intent);
			}
		});
		/*editStart = (EditText) view.findViewById(R.id.editTextStart);
		editEnd = (EditText) view.findViewById(R.id.editTextEnd);
		btnSearch = (Button) view.findViewById(R.id.button1);
		mTransitRouteLines = new ArrayList<TransitRouteLine>();
		mRoutePlanSearch = RoutePlanSearch.newInstance();
		mRoutePlanSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
			
			@Override
			public void onGetWalkingRouteResult(WalkingRouteResult arg0) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onGetTransitRouteResult(TransitRouteResult result) {
				// TODO Auto-generated method stub
			if(result == null || result.error != SearchResult.ERRORNO.NO_ERROR){
				Toast.makeText(getActivity().getBaseContext(),"抱歉，未找到结果" , Toast.LENGTH_SHORT).show();
				
			}
			if(result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR ){
				
				return;
			}
			if(result.error == SearchResult.ERRORNO.NO_ERROR){
				List<TransitRouteLine> mRouteLines =  result.getRouteLines();
				for (TransitRouteLine transitRouteLine : mRouteLines) {
					Log.d("transitRouteLine", transitRouteLine.getTitle()+transitRouteLine.getDistance()+"米,耗时"+transitRouteLine.getDuration()+"秒");
					List<TransitStep> mTransitSteps = transitRouteLine.getAllStep();
					for (TransitStep transitStep : mTransitSteps) {
						Log.d("routeline", transitStep.getInstructions());
						
						
					}
				}
				
			}
			
			}
			
			@Override
			public void onGetDrivingRouteResult(DrivingRouteResult arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		btnSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("onclick", "点击了查询方案");
				// TODO Auto-generated method stub
				PlanNode fromNode = PlanNode.withCityNameAndPlaceName("苏州", editStart.getText().toString());
				PlanNode toNode = PlanNode.withCityNameAndPlaceName("苏州", editEnd.getText().toString());
				TransitRoutePlanOption option = new TransitRoutePlanOption();
				option.from(fromNode)
				.to(toNode)
				.city("苏州");
				mRoutePlanSearch.transitSearch(option);
				
			}
		});*/
		
	/*	editStart.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity().getBaseContext(),TestActivity.class);
				intent.putExtra("position", "请输入起点..");
				startActivityForResult(intent,START_POSITION);
				Intent intent = new Intent(getActivity().getBaseContext(),TestActivity.class);
				startActivityForResult(intent, START_POSITION);
				return true;
			}
		});*/
		
		/*editStart.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				
			}
		});*/
	}
	
/*	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		//super.onActivityResult(requestCode, resultCode, data);
		String returnPosition = "null";
		if(resultCode ==android.app.Activity.RESULT_OK ){
			Log.d("onActivityResult", "OK");
			switch (requestCode) {
			case START_POSITION:
					returnPosition = data.getStringExtra("data_return");
					editStart.setText(returnPosition);
					Log.d("returnPosition", returnPosition);
				break;
			case END_POSITION:
				 returnPosition = data.getStringExtra("data_return");
				editEnd.setText(returnPosition);
			default:
				break;
			}
		}
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d("fragmentDestroy", "onDestroy");
	}
	*/
	
}
