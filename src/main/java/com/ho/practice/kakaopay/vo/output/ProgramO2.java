package com.ho.practice.kakaopay.vo.output;

public class ProgramO2 {

	private String theme;
    private String program_name;
    
    public ProgramO2() {
    }
	public ProgramO2(String programName, String themeString) {
		this.theme = themeString;
		this.program_name = programName;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getProgram_name() {
		return program_name;
	}
	public void setProgram_name(String program_name) {
		this.program_name = program_name;
	}
    
}
