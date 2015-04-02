package com.billionwang.entity;

public class CityAddress {
	private String cityAddressName;
	private String cityAddressRegion;
	
	public CityAddress(String cityAddressName, String cityAddressRegion) {
		super();
		this.cityAddressName = cityAddressName;
		this.cityAddressRegion = cityAddressRegion;
	}
	
	public String getCityAddressName() {
		return cityAddressName;
	}
	public void setCityAddressName(String cityAddressName) {
		this.cityAddressName = cityAddressName;
	}
	public String getCityAddressRegion() {
		return cityAddressRegion;
	}
	public void setCityAddressRegion(String cityAddressRegion) {
		this.cityAddressRegion = cityAddressRegion;
	}
	
	
}
