package com.billionwang.application;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.billionwang.activity.LineRouteActivity;
import com.example.baidubus.R;

import android.R.drawable;
import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class MyApplication extends Application{
	public LocationClient mLocationClient;
	public TextView mLocationResult;
	public ImageView mImageView;
	public MyLocationListener mMyLocationListener;
	public LatLng mLl ;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		SDKInitializer.initialize(getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient = new LocationClient(this.getApplicationContext());
		mLocationClient.registerLocationListener(mMyLocationListener);
	}

	public class MyLocationListener implements BDLocationListener{

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			Log.d("onReceiveLocation", location.getAddrStr());
			String resultAddres = location.getAddrStr();
			if(mLocationResult!=null){
				mLocationResult.setText(resultAddres);
				SharedPreferences.Editor editor = getSharedPreferences("LatLong", MODE_PRIVATE).edit();
				editor.putString("latitude", location.getLatitude()+"");
				editor.putString("longitude", location.getLongitude()+"");
				editor.commit();
			}
			if(mImageView !=null){
				mImageView.setImageDrawable(getResources().getDrawable(R.drawable.icon_location));
			}
			mLocationClient.stop();

		}

	}
}
