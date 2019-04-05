package com.ho.practice.kakaopay.model;

import java.io.Serializable;

public class ServiceRegionId implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6045669956563594656L;
	
	private String programCode;
	private String regionCode;
	
	public ServiceRegionId() {}
	
	public ServiceRegionId(String programCode, String regionCode) {
		this.programCode = programCode;
		this.regionCode = regionCode;
	}
	
}
