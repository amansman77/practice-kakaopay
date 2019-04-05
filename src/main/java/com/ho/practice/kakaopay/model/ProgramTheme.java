package com.ho.practice.kakaopay.model;

import javax.persistence.Column;
import javax.persistence.Id;

//@Entity
public class ProgramTheme {

	@Id
	@Column(columnDefinition = "TEXT", nullable = false)
	private String programId;
	@Id
	@Column(columnDefinition = "TEXT", nullable = false)
	private String themeId;

}
