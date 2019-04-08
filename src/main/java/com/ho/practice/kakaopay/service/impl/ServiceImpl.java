package com.ho.practice.kakaopay.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
	public List<TourInfo> getTourInfoByRegionCode(String regionCode) {
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
				
		// 프로그램 데이터 및 테마 조회
		Map<String, Program> programMap = this.getProgramMap(programCodeList);
		Map<String, String> themeStringMap = this.getProgramThemeMap(programCodeList);
		
		// 관광정보 데이터 설정
		Iterator<String> itr = programMap.keySet().iterator();
		while(itr.hasNext()) {
			String programCode = itr.next();
			Program program = programMap.get(programCode);
			String themeString = themeStringMap.get(programCode);
			tourInfoList.add(new TourInfo(program.getProgramCode(), program.getProgramName(), program.getProgramDesc(), program.getProgramDetailDesc()
					, themeString, region));
		}
		
		return tourInfoList;
	}

	@Override
	public List<TourInfo> getTourInfoByResionName(String regionName) {
		List<TourInfo> tourInfoList = new ArrayList<>();
		
		// 지역 목록 조회
		List<Region> regionList = regionRepository.findBySidoNameOrSggNameOrEmdName(regionName, regionName, regionName);

		for (Region region : regionList) {
			// 프로그램 정보 추출
			List<ServiceRegion> serviceRegionList = serviceRegionRepository.findByRegionCode(region.getRegionCode());
			List<String> programCodeList = new ArrayList<>();
			for (int i = 0; i < serviceRegionList.size(); i++) {
				if(!programCodeList.contains(serviceRegionList.get(i).getProgramCode())) {
					programCodeList.add(serviceRegionList.get(i).getProgramCode());
				}
			}
			
			// 프로그램 정보와 테마정보 조회
			Map<String, Program> programMap = this.getProgramMap(programCodeList);
			Map<String, String> themeStringMap = this.getProgramThemeMap(programCodeList);
			
			Iterator<String> itr = programMap.keySet().iterator();
			while(itr.hasNext()) {
				String programCode = itr.next();
				Program program = programMap.get(programCode);
				String themeString = themeStringMap.get(programCode);
				tourInfoList.add(new TourInfo(programCode, program.getProgramName(), program.getProgramDesc(), program.getProgramDetailDesc(), themeString, region));
			}
		}
		
		return tourInfoList;
	}

	@Override
	public List<TourInfo> getTourInfoSearchProgramDesc(String keyword) {
		List<TourInfo> tourInfoList = new ArrayList<>();
		
		List<Program> programList = programRepository.findByProgramDescContaining(keyword);
		// 프로그램 코드 추출
		List<String> programCodeList = new ArrayList<>();
		for (int i = 0; i < programList.size(); i++) {
			if(!programCodeList.contains(programList.get(i).getProgramCode())) {
				programCodeList.add(programList.get(i).getProgramCode());
			}
		}
		
		Map<String, List<Region>> regionMap = this.getRegionMap(programCodeList);
		Iterator<String> itr = regionMap.keySet().iterator();
		while(itr.hasNext()) {
			String programCode = itr.next();
			List<Region> regionList = regionMap.get(programCode);
			for (Region region : regionList) {
				tourInfoList.add(new TourInfo(programCode, null, null, null, null, region));
			}
		}
		
		return tourInfoList;
	}

	@Override
	public int getWordCountFromProgramDetailDesc(String keyword) {
		List<String> list = programRepository.findProgramDescAll();
		int count = 0;
		for (String detailDesc : list) {
			int nextIdx = 0;
			while(detailDesc != null) {
				nextIdx = detailDesc.indexOf(keyword, nextIdx);
				if(nextIdx < 0) {
					break;
				} else {
					nextIdx += keyword.length();
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * 프로그램 코드를 키로하는 Program 객체 맵 반환
	 * @param programCodeList
	 * @return
	 */
	private Map<String, Program> getProgramMap(List<String> programCodeList) {
		Map<String, Program> map = new HashMap<>();
		List<Program> programList = programRepository.findByProgramCodeIn(programCodeList);
		for (int i = 0; i < programList.size(); i++) {
			map.put(programList.get(i).getProgramCode(), programList.get(i));
		}
		return map;
	}

	@SuppressWarnings("serial")
	private Map<String, List<Region>> getRegionMap(List<String> programCodeList) {
		Map<String, List<Region>> map = new HashMap<>();
		
		List<ServiceRegion> serviceRegionList = serviceRegionRepository.findByProgramCodeIn(programCodeList);
		List<String> regionCodeList = new ArrayList<>();
		for (int i = 0; i < serviceRegionList.size(); i++) {
			if(!regionCodeList.contains(serviceRegionList.get(i).getRegionCode())) {
				regionCodeList.add(serviceRegionList.get(i).getRegionCode());
			}
		}
		
		List<Region> regionList = regionRepository.findByRegionCodeIn(regionCodeList);
		for (int i = 0; i < serviceRegionList.size(); i++) {
			ServiceRegion sr = serviceRegionList.get(i);
			for (Region region : regionList) {
				if(sr.getRegionCode().equals(region.getRegionCode())) {
					if(map.containsKey(sr.getProgramCode())) {
						// 이미 존재하면 테마 추가
						map.get(sr.getProgramCode()).add(region);
					} else {
						map.put(sr.getProgramCode(), new ArrayList<Region>() {{
						    add(region);
						}});
					}
					break;
				}
			}
		}
		
		return map;
	}

	/**
	 * 프로그램 코드를 키로하는 theme string 맵 반환
	 * @param programCodeList
	 * @return
	 */
	private Map<String, String> getProgramThemeMap(List<String> programCodeList) {
		Map<String, String> map = new HashMap<>();
		
		List<ProgramTheme> programThemeList = programThemeRepository.findByProgramCodeIn(programCodeList);
		List<String> themeCodeList = new ArrayList<>();
		for (int i = 0; i < programThemeList.size(); i++) {
			if(!themeCodeList.contains(programThemeList.get(i).getThemeCode())) {
				themeCodeList.add(programThemeList.get(i).getThemeCode());
			}
		}
		List<Theme> themeList = themeRepository.findByThemeCodeIn(themeCodeList);
		
		for (int i = 0; i < programThemeList.size(); i++) {
			ProgramTheme ph = programThemeList.get(i);
			for (Theme theme : themeList) {
				if(ph.getThemeCode().equals(theme.getThemeCode())) {
					if(map.containsKey(ph.getProgramCode())) {
						// 이미 존재하면 테마 추가
						map.put(ph.getProgramCode(), map.get(ph.getProgramCode()) + "," + theme.getThemeName());
					} else {
						map.put(ph.getProgramCode(), theme.getThemeName());
					}
					break;
				}
			}
		}
		
		return map;
	}
	
}
