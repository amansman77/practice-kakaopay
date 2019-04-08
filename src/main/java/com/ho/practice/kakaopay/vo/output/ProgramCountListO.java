package com.ho.practice.kakaopay.vo.output;

import java.util.Iterator;
import java.util.Map;

public class ProgramCountListO {

	private String keyword;
	private RegionCount[] programs;
	
	public ProgramCountListO() {
	}
	public ProgramCountListO(String keyword, Map<String, Integer> countMap) {
		this.setKeyword(keyword);
		
		this.programs = new RegionCount[countMap.size()];
		Iterator<String> itr = countMap.keySet().iterator();
		int idx = 0;
		while (itr.hasNext()) {
			String address = itr.next();
			programs[idx++] = new RegionCount(address, countMap.get(address));
		}
	}
    
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public RegionCount[] getPrograms() {
		return programs;
	}
	
	@SuppressWarnings("unused")
	private class RegionCount {
		private String region;
		private int count;
		
		public RegionCount() {
		}
		public RegionCount(String region, Integer count) {
			this.setRegion(region);
			this.setCount(count);
		}
		public String getRegion() {
			return region;
		}
		public void setRegion(String region) {
			this.region = region;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
	}
	
}
