package com.billionwang.entity;



/**������Ϣʵ����
 * @author AlbertWang
 * @created 2015/3/18
 * 
 */
public class TransitRouteLineInfo {
	private String transitLine;
	private String arrivedTime;
	private String duration;
	private String distance;
	private String walkingLenth;
	
	/**������Ϣʵ����
	 * @param transitLine  ������·
	 * @param arrivedTime  ����ʱ��
	 * @param duration	         ��ʱ
	 * @param distance     ����
	 * @param walkingLenth ���о���
	 */
	public TransitRouteLineInfo(String transitLine, String arrivedTime,
			String duration, String distance, String walkingLenth) {
		super();
		this.transitLine = transitLine;
		this.arrivedTime = arrivedTime;
		this.duration = duration;
		this.distance = distance;
		this.walkingLenth = walkingLenth;
	}

	public String getTransitLine() {
		return transitLine;
	}

	public void setTransitLine(String transitLine) {
		this.transitLine = transitLine;
	}

	public String getArrivedTime() {
		return arrivedTime;
	}

	public void setArrivedTime(String arrivedTime) {
		this.arrivedTime = arrivedTime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getWalkingLenth() {
		return walkingLenth;
	}

	public void setWalkingLenth(String walkingLenth) {
		this.walkingLenth = walkingLenth;
	}
	
	
	
	
}
