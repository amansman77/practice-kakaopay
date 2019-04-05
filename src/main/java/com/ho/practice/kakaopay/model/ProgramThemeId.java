package com.ho.practice.kakaopay.model;

import java.io.Serializable;

public class ProgramThemeId implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6045669956563594656L;
	
	private String programCode;
	private String themeCode;
	
	public ProgramThemeId() {}
	
	public ProgramThemeId(String programCode, String themeCode) {
		this.programCode = programCode;
		this.programCode = programCode;
	}
	
}
