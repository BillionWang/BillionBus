package com.billionwang.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.billionwang.entity.TransitRouteLineInfo;
import com.example.baidubus.R;

public class TransiRouteAdapter extends BaseAdapter{
	private Context mContext;
	private ArrayList<TransitRouteLineInfo> mTransitRouteLineInfos;
	private LayoutInflater mInflater;
	private TransitRouteLineInfo mTransitRouteLineInfo ;
	
	public TransiRouteAdapter(Context context,ArrayList<TransitRouteLineInfo> transitRouteLineInfos){
		this.mContext = context;
		this.mTransitRouteLineInfos = transitRouteLineInfos;
		this.mInflater = LayoutInflater.from(context);
				
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mTransitRouteLineInfos.size();
		
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
			convertView = mInflater.inflate(R.layout.listview_transi_route, null);
			holder = new Viewholder();
			holder.tvTransiRoute = (TextView) convertView.findViewById(R.id.tvTransiRoute);
			holder.tvArrivedTime = (TextView) convertView.findViewById(R.id.tvArrivedTime);
			holder.tvDuration = (TextView) convertView.findViewById(R.id.tvDuration);
			holder.tvDistance = (TextView) convertView.findViewById(R.id.tvDistance);
			holder.tvWalkLength = (TextView) convertView.findViewById(R.id.tv_walk_lenth);
			convertView.setTag(holder);
		}
		
		else {
			holder = (Viewholder) convertView.getTag();
		}
		
		mTransitRouteLineInfo = mTransitRouteLineInfos.get(position);
		holder.tvTransiRoute.setText(mTransitRouteLineInfo.getTransitLine());
		holder.tvArrivedTime.setText(mTransitRouteLineInfo.getArrivedTime());
		holder.tvDuration.setText(mTransitRouteLineInfo.getDuration());
		holder.tvDistance.setText(mTransitRouteLineInfo.getDistance());
		holder.tvWalkLength.setText(mTransitRouteLineInfo.getWalkingLenth());
		return convertView;
	}
	
	public class Viewholder{
		TextView tvTransiRoute;
		TextView tvArrivedTime;
		TextView tvDuration;
		TextView tvDistance;
		TextView tvWalkLength;
		
	}
}
