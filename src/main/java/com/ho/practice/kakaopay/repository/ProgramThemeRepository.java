package com.ho.practice.kakaopay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ho.practice.kakaopay.model.ProgramTheme;

@Repository
public interface ProgramThemeRepository extends JpaRepository<ProgramTheme, Long>{

	List<ProgramTheme> findByProgramCode(String programCode);

	List<ProgramTheme> findByProgramCodeIn(List<String> programCodeList);
	
}
