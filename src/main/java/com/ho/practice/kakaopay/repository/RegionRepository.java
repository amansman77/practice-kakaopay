package com.ho.practice.kakaopay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ho.practice.kakaopay.model.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long>{

	Region findBySidoNameAndSggNameAndEmdNameAndDetailAddress(String sidoName, String sggName, String emdName, String detailAddress);
	
}
