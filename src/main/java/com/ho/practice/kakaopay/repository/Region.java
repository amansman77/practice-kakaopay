package com.ho.practice.kakaopay.repository;

public class Region {

	/**
	 * 서비스 지역 코드
	 */
	private String regionId;
	private String regionName;
	private String sidoName;
	private String sggName;
	private String emdName;
	
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getSidoName() {
		return sidoName;
	}
	public void setSidoName(String sidoName) {
		this.sidoName = sidoName;
	}
	public String getSggName() {
		return sggName;
	}
	public void setSggName(String sggName) {
		this.sggName = sggName;
	}
	public String getEmdName() {
		return emdName;
	}
	public void setEmdName(String emdName) {
		this.emdName = emdName;
	}

}
