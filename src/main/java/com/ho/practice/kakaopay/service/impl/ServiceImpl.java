package com.ho.practice.kakaopay.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ho.practice.kakaopay.dto.TourInfo;
import com.ho.practice.kakaopay.model.Program;
import com.ho.practice.kakaopay.model.ProgramTheme;
import com.ho.practice.kakaopay.model.Region;
import com.ho.practice.kakaopay.model.ServiceRegion;
import com.ho.practice.kakaopay.model.Theme;
import com.ho.practice.kakaopay.repository.ProgramRepository;
import com.ho.practice.kakaopay.repository.ProgramThemeRepository;
import com.ho.practice.kakaopay.repository.RegionRepository;
import com.ho.practice.kakaopay.repository.ServiceRegionRepository;
import com.ho.practice.kakaopay.repository.ThemeRepository;
import com.ho.practice.kakaopay.service.Service;
import com.ho.practice.kakaopay.util.AddressParser;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

	@Autowired
	ProgramRepository programRepository;
	@Autowired
	ThemeRepository themeRepository;
	@Autowired
	RegionRepository regionRepository;
	@Autowired
	ProgramThemeRepository programThemeRepository;
	@Autowired
	ServiceRegionRepository serviceRegionRepository;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	@Override
	public void saveTourInfo(String programCode, String programName, String programDesc, String programDetailDesc
			, String themeString, String regionString) throws Exception {
		Program program = new Program(programCode, programName, programDesc, programDetailDesc);
		program = programRepository.save(program);
		
		List<ProgramTheme> deleteProgramThemeList = new ArrayList<>();
		List<ServiceRegion> deleteServiceRegionList = new ArrayList<>();
		if(programCode != null) {
			// 갱신 상황에선 관계 삭제
			deleteProgramThemeList = programThemeRepository.findByProgramCode(program.getProgramCode());
			deleteServiceRegionList = serviceRegionRepository.findByProgramCode(program.getProgramCode());
		}
		
		// 테마 저장
		String[] tokens = themeString.split(",");
		for (int i = 0; i < tokens.length; i++) {
			String themeName = tokens[i];
			if(StringUtils.isEmpty(themeName)) {
				continue;
			}
			
			Theme theme = themeRepository.findByThemeName(tokens[i]);
			if(theme == null) {
				// 테마가 존재하지 않으면 추가
				theme = new Theme(tokens[i]);
				theme = themeRepository.save(theme);
			}
			
			if(deleteProgramThemeList.size() > 1) {
				for (int j = 0; j < deleteProgramThemeList.size(); j++) {
					if(program.getProgramCode().equalsIgnoreCase(deleteProgramThemeList.get(j).getProgramCode())
							&& theme.getThemeCode().equalsIgnoreCase(deleteProgramThemeList.get(j).getThemeCode())) {
						deleteProgramThemeList.remove(j);
						break;
					}
				}
			}
			programThemeRepository.save(new ProgramTheme(program.getProgramCode(), theme.getThemeCode()));
		}
		
		// 주소 저장
		tokens = regionString.split(",");
		Region preRegion = new Region();
		for (int i = 0; i < tokens.length; i++) {
			String address = tokens[i];
			if(StringUtils.isEmpty(address)) {
				continue;
			}
			
			// 주소 파싱
			AddressParser parser = new AddressParser();
			Region region = parser.parse(address);
			if(StringUtils.isEmpty(region.getSidoName())) {
				region.setSidoName(preRegion.getSidoName());
			}
			Region saveRegion = regionRepository.findBySidoNameAndSggNameAndEmdNameAndDetailAddress(region.getSidoName(), region.getSggName(), region.getEmdName(), region.getDetailAddress());
			if(saveRegion == null) {
				// 지역이 존재하지 않으면 추가
				region = regionRepository.save(region);
			} else {
				region.setRegionCode(saveRegion.getRegionCode());
			}
			
			if(deleteServiceRegionList.size() > 1) {
				for (int j = 0; j < deleteServiceRegionList.size(); j++) {
					if(program.getProgramCode().equalsIgnoreCase(deleteServiceRegionList.get(j).getProgramCode())
							&& region.getRegionCode().equalsIgnoreCase(deleteServiceRegionList.get(j).getRegionCode())) {
						deleteServiceRegionList.remove(j);
						break;
					}
				}
			}
			serviceRegionRepository.save(new ServiceRegion(program.getProgramCode(), region.getRegionCode()));
			
			preRegion = region;
		}
		
		// 관계 삭제
		for (int i = 0; i < deleteProgramThemeList.size(); i++) {
			programThemeRepository.delete(new ProgramTheme(deleteProgramThemeList.get(i).getProgramCode(), deleteProgramThemeList.get(i).getThemeCode()));
		}
		for (int i = 0; i < deleteServiceRegionList.size(); i++) {
			serviceRegionRepository.delete(new ServiceRegion(deleteServiceRegionList.get(i).getProgramCode(), deleteServiceRegionList.get(i).getRegionCode()));
		}
	}

	@Override
	public List<TourInfo> getTourInfo(String regionCode) {
		List<TourInfo> tourInfoList = new ArrayList<>();
		
		// 지역에 속한 프로그래 목록 조회
		List<ServiceRegion> serviceRegionList = serviceRegionRepository.findByRegionCode(regionCode);
		
		// 프로그램 지역정보 조회
		Region region = regionRepository.findByRegionCode(regionCode);
				
		// 프로그램 코드 추출
		List<String> programCodeList = new ArrayList<>();
		for (int i = 0; i < serviceRegionList.size(); i++) {
			if(!programCodeList.contains(serviceRegionList.get(i).getProgramCode())) {
				programCodeList.add(serviceRegionList.get(i).getProgramCode());
			}
		}
				
		// 프로그램 데이터 조회
		List<Program> programList = programRepository.findByProgramCodeIn(programCodeList);
				
		// 프로그램 테마 조회
		List<ProgramTheme> programThemeList = programThemeRepository.findByProgramCodeIn(programCodeList);
		List<String> themeCodeList = new ArrayList<>();
		for (int i = 0; i < programThemeList.size(); i++) {
			if(!themeCodeList.contains(programThemeList.get(i).getThemeCode())) {
				themeCodeList.add(programThemeList.get(i).getThemeCode());
			}
		}
		List<Theme> themeList = themeRepository.findByThemeCodeIn(themeCodeList);
		
		// 관광정보 데이터 설정
		for (int i = 0; i < programList.size(); i++) {
			Program program = programList.get(i);
			String themeString = "";
			for (int j = 0; j < programThemeList.size(); j++) {
				if(program.getProgramCode().equals(programThemeList.get(j).getProgramCode())) {
					// 동일한 프로그램이면 테마 추가
					for (int j2 = 0; j2 < themeList.size(); j2++) {
						if(programThemeList.get(j).getThemeCode().equals(themeList.get(j2).getThemeCode())) {
							if(themeString.length() > 0) {
								themeString += ",";
							}
							themeString += themeList.get(j2).getThemeName();
						}
					}
				}
			}
			tourInfoList.add(new TourInfo(program.getProgramCode(), program.getProgramName(), program.getProgramDesc(), program.getProgramDetailDesc()
					, themeString, region));
		}
		
		return tourInfoList;
	}

}
