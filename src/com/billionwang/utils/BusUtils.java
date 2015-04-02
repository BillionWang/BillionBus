package com.billionwang.utils;

import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.R.integer;
import android.util.Log;



/**
 * 时间操作类
 * @author AlbertWang
 * @version 1.0
 * @created 2015-3-17
 */
public class BusUtils {
	
	
	/**给公交车首发车时间加上0
	 * @param mDate 
	 * @return
	 */
	public static String addZeroBeforeTime(Date mDate){
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(mDate);
		int mHour = calendar.get(Calendar.HOUR_OF_DAY);
		int mMinute =calendar.get(Calendar.MINUTE);
		String hourString = String.valueOf(mHour);
		String minuteString = String.valueOf(mMinute);
		if(mHour<10){
			hourString = "0"+String.valueOf(mHour);
		}
		
		if(mMinute<10){
			minuteString = "0" +String.valueOf(mMinute);
		}
		return hourString+":"+minuteString;
	}
	
	
	/**获得出发时间
	 * @return 返回出发时间
	 */
	public static String getStartTime(){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		String month = String.valueOf(c.get(Calendar.MONTH)+1); 
		Log.d(month, month);
		String date =  String.valueOf(c.get(Calendar.DATE)); 
		String hourAndMinute = addZeroBeforeTime(c.getTime());
		StringBuffer sb = new StringBuffer();
		sb.append(month).append("月").append(date).append("日 ")
		.append(hourAndMinute).append(" 出发");
		return sb.toString();
	}
	
	
	/**根据米返回千米字符串
	 * @param lenth 米
	 * @return 千米
	 */
	public static String getKm(int lenth){
		float result = lenth/1000;
		return String.valueOf(result)+"公里";
	}
	
	/**秒转为小时和分钟
	 * @param seconds 秒
	 * @return 分钟和小时
	 */
	public static String getHour(int seconds){
		int mHour = seconds/3600;
		int mMinute = (seconds%3600)/60;
		StringBuffer sb = new StringBuffer();
		sb.append(mHour).append("小时").append(mMinute).append("分钟");
		return sb.toString();
	}
	
	/**获得换乘信息
	 * @param transiLine 站点信息
	 * @return 换乘信息字符串
	 */
	public static String getTransiLine(ArrayList<String> transiLine){
		StringBuffer sb = new StringBuffer();
		boolean flag = false; //在前面加-<
		for (String string : transiLine) {
			if(flag){
				sb.append("->");
			}
			String lineName = string.substring(2, string.length());
			sb.append(lineName);
			flag = true;
		}
		return sb.toString();
	}
}
