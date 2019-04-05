package com.ho.practice.kakaopay.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Theme {

	@Id
	@GenericGenerator(name = "theme_code", strategy = "com.ho.practice.kakaopay.model.generator.ThemeCodeGenerator")
    @GeneratedValue(generator = "theme_code")
	@Column(name="theme_code", columnDefinition = "varchar(8)", unique=true)
	private String themeCode;
	
	@Column(columnDefinition = "varchar(16)", nullable = false)
	private String themeName;
	
	public Theme(String themeName) {
		this.themeName = themeName;
	}
	
}
