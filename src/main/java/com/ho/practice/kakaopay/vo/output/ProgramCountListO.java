package com.ho.practice.kakaopay.vo.output;

import java.util.Iterator;
import java.util.Map;

public class ProgramCountListO {

	private String keyword;
	private RegionCountO[] programs;
	
	public ProgramCountListO() {
	}
	public ProgramCountListO(String keyword, Map<String, Integer> countMap) {
		this.keyword = keyword;
		
		this.programs = new RegionCountO[countMap.size()];
		Iterator<String> itr = countMap.keySet().iterator();
		int idx = 0;
		while (itr.hasNext()) {
			String address = itr.next();
			programs[idx++] = new RegionCountO(address, countMap.get(address));
		}
	}
	public String getKeyword() {
		return this.keyword;
	}
	public RegionCountO[] getPrograms() {
		return this.programs;
	}
    
}
