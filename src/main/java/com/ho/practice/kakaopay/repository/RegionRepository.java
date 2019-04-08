package com.ho.practice.kakaopay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ho.practice.kakaopay.model.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long>{

	Region findBySidoNameAndSggNameAndEmdNameAndDetailAddress(String sidoName, String sggName, String emdName, String detailAddress);

	Region findByRegionCode(String regionCode);

	List<Region> findByRegionCodeIn(List<String> regionCodeList);

	List<Region> findBySidoNameOrSggNameOrEmdName(String regionName, String regionName2, String regionName3);

}
