package com.ho.practice.kakaopay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ho.practice.kakaopay.model.Theme;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long>{
	
	Theme findByThemeName(String themeName);

	List<Theme> findByThemeCodeIn(List<String> themeCodeList);

}
