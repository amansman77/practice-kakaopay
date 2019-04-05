package com.ho.practice.kakaopay.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(ProgramThemeId.class)
public class ProgramTheme implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1334218115369725067L;

	@Id
	@Column(name="program_code", columnDefinition = "varchar(8)", unique=false)
	private String programCode;
	
	@Id
	@Column(name="theme_code", columnDefinition = "varchar(8)", unique=false)
	private String themeCode;

	public ProgramTheme() {
	}
	public ProgramTheme(String programCode, String themeCode) {
		this.programCode = programCode;
		this.themeCode = themeCode;
	}
	
}
