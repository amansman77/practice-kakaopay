package com.ho.practice.kakaopay.vo.output;

public class ProgramListO {

	private String region_id;
	private ProgramO[] programs;
	
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
