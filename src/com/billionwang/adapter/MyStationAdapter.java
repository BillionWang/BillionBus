package com.billionwang.adapter;

import java.util.ArrayList;




import com.example.baidubus.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyStationAdapter extends BaseAdapter{

	private Context mContext;
	private ArrayList<String> mStationList;
	private LayoutInflater mInflater;
	
	public MyStationAdapter(Context context,ArrayList<String> stationList){
		this.mContext = context;
		this.mStationList = stationList;
		this.mInflater = LayoutInflater.from(context);
				
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.d("mStationList", mStationList.size()+"");
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
			convertView = mInflater.inflate(R.layout.listview_station_list, null);
			
			holder = new Viewholder();
			holder.tView = (TextView) convertView.findViewById(R.id.tv_line_detail);
			convertView.setTag(holder);
		}
		else {
			holder = (Viewholder) convertView.getTag();
		}
		
		holder.tView.setText(mStationList.get(position).toString());
		Log.d("tView", mStationList.get(position).toString());
		return convertView;
		
	}
	
	
	public class Viewholder{
		TextView tView;
	}
}
