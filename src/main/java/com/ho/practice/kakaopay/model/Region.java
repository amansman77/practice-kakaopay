package com.ho.practice.kakaopay.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.StringUtils;

import lombok.Data;

@Data
@Entity
public class Region {

	@Id
	@GenericGenerator(name = "region_code", strategy = "com.ho.practice.kakaopay.model.generator.RegionCodeGenerator")
    @GeneratedValue(generator = "region_code")
	@Column(name="region_code", columnDefinition = "varchar(8)", unique=true)
	private String regionCode;
	
	@Column(columnDefinition = "varchar(16)")
	private String sidoName;
	@Column(columnDefinition = "varchar(16)")
	private String sggName;
	@Column(columnDefinition = "varchar(16)")
	private String emdName;
	@Column(columnDefinition = "varchar(32)")
	private String detailAddress;
	
	public void setDetailAddress(String detailAddress) {
		if("일대".equalsIgnoreCase(detailAddress) || "일원".equalsIgnoreCase(detailAddress)) {
			return;
		}
		this.detailAddress = detailAddress;
	}

	public String getAddress() {
		String s = sidoName;
		if(!StringUtils.isEmpty(sggName)) {
			s += " " + sggName;
		}
		if(!StringUtils.isEmpty(emdName)) {
			s += " " + emdName;
		}
		if(!StringUtils.isEmpty(detailAddress)) {
			s += " " + detailAddress;
		}
		return s;
	}
}
