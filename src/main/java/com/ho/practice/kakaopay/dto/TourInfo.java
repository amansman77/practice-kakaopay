package com.ho.practice.kakaopay.dto;

import com.ho.practice.kakaopay.model.Region;

import lombok.Data;

@Data
public class TourInfo {
	
	String programId;
	String programName;
	String programDesc;
	String programDetailDesc;
	String themeString;
	String regionString;
	
	public TourInfo() {}
	public TourInfo(String programCode, String programName, String programDesc, String programDetailDesc, String themeString, Region region) {
		this.programId = programCode;
		this.programName = programName;
		this.programDesc = programDesc;
		this.programDetailDesc = programDetailDesc;
		this.themeString = themeString;
		this.regionString = region.getAddress();
	}
	
}
