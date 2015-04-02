package com.billionwang.adapter;

import java.util.ArrayList;

import com.billionwang.entity.CityAddress;
import com.example.baidubus.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyCityAddressAdapter extends BaseAdapter{

	private Context mContext;
	private ArrayList<CityAddress> mStationList;
	private LayoutInflater mInflater;
	private CityAddress mCityAddress ;
	
	public MyCityAddressAdapter(Context context,ArrayList<CityAddress> stationList){
		this.mContext = context;
		this.mStationList = stationList;
		this.mInflater = LayoutInflater.from(context);
				
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mStationList.size();
		
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Viewholder holder;
	
		if(convertView ==null){
			convertView = mInflater.inflate(R.layout.listview_city_address, null);
			holder = new Viewholder();
			holder.tvCityAddress = (TextView) convertView.findViewById(R.id.tvAddress);
			holder.tvCityRegion = (TextView) convertView.findViewById(R.id.tvRegion);
			convertView.setTag(holder);
		}
		else {
			holder = (Viewholder) convertView.getTag();
		}
		
		mCityAddress = mStationList.get(position);
		holder.tvCityAddress.setText(mCityAddress.getCityAddressName());
		holder.tvCityRegion.setText(mCityAddress.getCityAddressRegion());
		return convertView;
	}
	
	public class Viewholder{
		TextView tvCityAddress;
		TextView tvCityRegion;
	}
	
}
