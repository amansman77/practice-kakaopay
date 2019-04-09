package com.ho.practice.kakaopay.vo.input;

import lombok.Data;

@Data
public class DataUpdateI {

	private String program_code;
	private String program_theme;
    private String program_name;
    private String program_desc;
    private String program_detail_desc;
    private String region;
    
    public DataUpdateI() {
    }
    public DataUpdateI(String program_code, String program_name, String program_desc, String program_detail_desc, String program_theme, String region) {
    	this.program_code = program_code;
    	this.program_name = program_name;
    	this.program_desc = program_desc;
    	this.program_detail_desc = program_detail_desc;
    	this.program_theme = program_theme;
    	this.region = region;
	}
    
}
