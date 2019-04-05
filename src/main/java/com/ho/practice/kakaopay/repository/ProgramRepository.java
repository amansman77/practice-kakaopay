package com.ho.practice.kakaopay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ho.practice.kakaopay.model.Program;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long>{

	List<Program> findByProgramCodeIn(List<String> programCodeList);
	
}
