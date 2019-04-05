package com.ho.practice.kakaopay.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

//@Entity
public class ServiceRegion {

	@Id
	@Column(columnDefinition = "TEXT", nullable = false)
	private String programId;
	@Id
	@Column(columnDefinition = "TEXT", nullable = false)
	private String regionId;

}
