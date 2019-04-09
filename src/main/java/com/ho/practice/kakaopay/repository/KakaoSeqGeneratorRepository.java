package com.ho.practice.kakaopay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ho.practice.kakaopay.model.KakaoSeqGenerator;

@Repository
public interface KakaoSeqGeneratorRepository extends JpaRepository<KakaoSeqGenerator, Long>{

	KakaoSeqGenerator findBySequenceName(String sequenceName);
	
}
