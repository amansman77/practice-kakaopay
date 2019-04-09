package com.ho.practice.kakaopay.vo.output;

public class RegionCountO {

	private String region;
	private int count;
	
	public RegionCountO() {
	}
	public RegionCountO(String region, Integer count) {
		this.region = region;
		this.count = count;
	}
	public String getRegion() {
		return this.region;
	}
	public int getCount() {
		return this.count;
	}
	
}
