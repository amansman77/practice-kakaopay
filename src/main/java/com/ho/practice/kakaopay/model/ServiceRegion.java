package com.ho.practice.kakaopay.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(ServiceRegionId.class)
public class ServiceRegion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1999497270929777583L;
	
	@Id
	@Column(name="program_code", columnDefinition = "varchar(8)", unique=false)
	private String programCode;
	
	@Id
	@Column(name="region_code", columnDefinition = "varchar(8)", unique=false)
	private String regionCode;

	public ServiceRegion() {
	}
	public ServiceRegion(String programCode, String regionCode) {
		this.programCode = programCode;
		this.regionCode = regionCode;
	}
	
}
