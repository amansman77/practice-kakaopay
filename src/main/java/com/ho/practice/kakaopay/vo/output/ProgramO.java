package com.ho.practice.kakaopay.vo.output;

import com.ho.practice.kakaopay.dto.TourInfo;

public class ProgramO {

	private String program_id;
	private String program_theme;
    private String program_name;
    private String program_desc;
    private String program_detail_desc;
    private String region;
    
    public ProgramO() {}
	public ProgramO(TourInfo tourInfo) {
		this.program_id = "";
		this.program_theme = tourInfo.getThemeString();
		this.program_name = tourInfo.getProgramName();
		this.program_desc = tourInfo.getProgramDesc();
		this.program_detail_desc = tourInfo.getProgramDetailDesc();
		this.region = tourInfo.getRegionString();
	}
	
	public String getProgram_id() {
		return program_id;
	}
	public void setProgram_id(String program_id) {
		this.program_id = program_id;
	}
	public String getProgram_theme() {
		return program_theme;
	}
	public void setProgram_theme(String program_theme) {
		this.program_theme = program_theme;
	}
	public String getProgram_name() {
		return program_name;
	}
	public void setProgram_name(String program_name) {
		this.program_name = program_name;
	}
	public String getProgram_desc() {
		return program_desc;
	}
	public void setProgram_desc(String program_desc) {
		this.program_desc = program_desc;
	}
	public String getProgram_detail_desc() {
		return program_detail_desc;
	}
	public void setProgram_detail_desc(String program_detail_desc) {
		this.program_detail_desc = program_detail_desc;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
    
}
