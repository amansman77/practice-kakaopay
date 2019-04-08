package com.ho.practice.kakaopay.vo.output;

import java.util.List;

import com.ho.practice.kakaopay.dto.TourInfo;

public class ProgramListO2 {

	private String region;
	private ProgramO2[] programs;
	
	public ProgramListO2(List<TourInfo> list) {
		if(list.size() > 0) {
			this.region = list.get(0).getRegionCode();
			programs = new ProgramO2[list.size()];
			for (int i = 0; i < programs.length; i++) {
				programs[i] = new ProgramO2(list.get(i).getProgramName(), list.get(i).getThemeString());
			}
		}
	}
	
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public ProgramO2[] getPrograms() {
		return programs;
	}
	public void setPrograms(ProgramO2[] programs) {
		this.programs = programs;
	}
    
}
