package com.ho.practice.kakaopay.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class KakaoSeqGenerator {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long seqId;
	
	@Column(columnDefinition = "varchar(16)")
	private String sequenceName;
	
	@Column(columnDefinition = "long")
	private Long nextVal;

	public void increseNextVal() {
		this.nextVal++;
	}

	public KakaoSeqGenerator() {
	}
	public KakaoSeqGenerator(String sequenceName, Long nextVal) {
		this.sequenceName = sequenceName;
		this.nextVal = nextVal;
	}
	
}
