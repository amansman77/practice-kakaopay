package com.ho.practice.kakaopay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ho.practice.kakaopay.model.ServiceRegion;

@Repository
public interface ServiceRegionRepository extends JpaRepository<ServiceRegion, Long>{

	List<ServiceRegion> findByProgramCode(String programCode);

	List<ServiceRegion> findByRegionCode(String regionCode);

	List<ServiceRegion> findByRegionCodeIn(List<String> regionCodeList);

	List<ServiceRegion> findByProgramCodeIn(List<String> programCodeList);

}
