package com.ho.practice.kakaopay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ho.practice.kakaopay.model.ServiceRegion;

@Repository
public interface ServiceRegionRepository extends JpaRepository<ServiceRegion, Long>{
}
