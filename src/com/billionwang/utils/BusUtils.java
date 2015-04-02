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
 * ʱ�������
 * @author AlbertWang
 * @version 1.0
 * @created 2015-3-17
 */
public class BusUtils {
	
	
	/**���������׷���ʱ�����0
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
	
	
	/**��ó���ʱ��
	 * @return ���س���ʱ��
	 */
	public static String getStartTime(){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		String month = String.valueOf(c.get(Calendar.MONTH)+1); 
		Log.d(month, month);
		String date =  String.valueOf(c.get(Calendar.DATE)); 
		String hourAndMinute = addZeroBeforeTime(c.getTime());
		StringBuffer sb = new StringBuffer();
		sb.append(month).append("��").append(date).append("�� ")
		.append(hourAndMinute).append(" ����");
		return sb.toString();
	}
	
	
	/**�����׷���ǧ���ַ���
	 * @param lenth ��
	 * @return ǧ��
	 */
	public static String getKm(int lenth){
		float result = lenth/1000;
		return String.valueOf(result)+"����";
	}
	
	/**��תΪСʱ�ͷ���
	 * @param seconds ��
	 * @return ���Ӻ�Сʱ
	 */
	public static String getHour(int seconds){
		int mHour = seconds/3600;
		int mMinute = (seconds%3600)/60;
		StringBuffer sb = new StringBuffer();
		sb.append(mHour).append("Сʱ").append(mMinute).append("����");
		return sb.toString();
	}
	
	/**��û�����Ϣ
	 * @param transiLine վ����Ϣ
	 * @return ������Ϣ�ַ���
	 */
	public static String getTransiLine(ArrayList<String> transiLine){
		StringBuffer sb = new StringBuffer();
		boolean flag = false; //��ǰ���-<
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
