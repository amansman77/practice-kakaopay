package com.ho.practice.kakaopay.vo.input;

import lombok.Data;

@Data
public class DataInsertI {

	private String program_theme;
    private String program_name;
    private String program_desc;
    private String program_detail_desc;
    private String region;
    
    public DataInsertI() {
    }
    
	public DataInsertI(String programName, String programDesc, String programDetailDesc, String themeString, String regionString) {
		this.program_theme = themeString;
		this.program_name = programName;
		this.program_desc = programDesc;
		this.program_detail_desc = programDetailDesc;
		this.region = regionString;
	}
	
}
