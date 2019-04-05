package com.ho.practice.kakaopay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ho.practice.kakaopay.model.ProgramTheme;

@Repository
public interface ProgramThemeRepository extends JpaRepository<ProgramTheme, Long>{
}
