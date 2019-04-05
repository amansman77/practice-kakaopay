package com.ho.practice.kakaopay.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
public class Program {

	@Id
	@GenericGenerator(name = "program_code", strategy = "com.ho.practice.kakaopay.model.generator.ProgramCodeGenerator")
    @GeneratedValue(generator = "program_code")
	@Column(name="program_code", columnDefinition = "varchar(8)", unique=true)
	private String programCode;
	
	@Column(columnDefinition = "varchar(128)", nullable=false)
	private String programName;
	@Column(columnDefinition = "varchar(256)", nullable=true)
	private String programDesc;
	@Column(columnDefinition = "TEXT", nullable=true)
	private String programDetailDesc;
	
	public Program() {
		
	}
	public Program(String programCode, String programName, String programDesc, String programDetailDesc) {
		this.programCode = programCode;
		this.programName = programName;
		this.programDesc = programDesc;
		this.programDetailDesc = programDetailDesc;
	}
	
}
