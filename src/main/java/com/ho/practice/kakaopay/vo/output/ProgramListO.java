package com.ho.practice.kakaopay.vo.output;

import java.util.List;

import com.ho.practice.kakaopay.dto.TourInfo;

public class ProgramListO {

	private String region_id;
	private ProgramO[] programs;
	
	public ProgramListO() {
	}
	public ProgramListO(String regionCode, List<TourInfo> list) {
		this.region_id = regionCode;
		programs = new ProgramO[list.size()];
		for (int i = 0; i < list.size(); i++) {
			programs[i] = new ProgramO(list.get(i));
		}
	}
	
	public String getRegion_id() {
		return region_id;
	}
	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}
	public ProgramO[] getPrograms() {
		return programs;
	}
	public void setPrograms(ProgramO[] programs) {
		this.programs = programs;
	}
    
}
