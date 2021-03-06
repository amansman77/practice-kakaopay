package com.ho.practice.kakaopay.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ho.practice.kakaopay.dto.TourInfo;
import com.ho.practice.kakaopay.service.Service;
import com.ho.practice.kakaopay.util.CsvParser;
import com.ho.practice.kakaopay.vo.input.DataInsertI;
import com.ho.practice.kakaopay.vo.input.DataUpdateI;
import com.ho.practice.kakaopay.vo.output.ProgramCountListO;
import com.ho.practice.kakaopay.vo.output.ProgramListO;
import com.ho.practice.kakaopay.vo.output.ProgramListO2;
import com.ho.practice.kakaopay.vo.output.ResultMapO;
import com.ho.practice.kakaopay.vo.output.ResultO;
import com.ho.practice.kakaopay.vo.output.WordCountO;

@RestController
public class Controller {
	
	@Autowired
	Service service;
	
	@RequestMapping(value = "/init/database", method = RequestMethod.POST)
    public ResultO initDatabase() throws Exception {
		// 테이블 구축
		service.initDatabase();
		
		// CSV 파일 불러오기
		ClassPathResource classPathResource = new ClassPathResource("data/2_2017.csv");
		CsvParser csvParser = new CsvParser(classPathResource.getInputStream(), 6);
		if(csvParser.hasNext()) {
			// 헤더 버림
			csvParser.next();
		}
		while(csvParser.hasNext()) {
			String[] columns = csvParser.next();
			service.saveTourInfo(null, columns[1], columns[4], columns[5], columns[2], columns[3]);
		}
		
        return new ResultO("true");
    }
	
	/**
	 * 관광정보 데이터 추가
	 * @param dataInsertI
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/data", method = RequestMethod.POST)
    public ResultO addData(@RequestBody(required=true) DataInsertI dataInsertI) throws Exception {
		service.saveTourInfo(null, dataInsertI.getProgram_name(), dataInsertI.getProgram_desc(), dataInsertI.getProgram_detail_desc()
				, dataInsertI.getProgram_theme(), dataInsertI.getRegion());
		return new ResultO("true");
    }
	
	/**
	 * 관광정보 데이터 수정
	 * @param dataUpdateI
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/data", method = RequestMethod.PUT)
    public ResultO updateData(@RequestBody(required=true) DataUpdateI dataUpdateI) throws Exception {
		service.saveTourInfo(dataUpdateI.getProgram_code(), dataUpdateI.getProgram_name(), dataUpdateI.getProgram_desc(), dataUpdateI.getProgram_detail_desc()
				, dataUpdateI.getProgram_theme(), dataUpdateI.getRegion());
        return new ResultO("true");
    }
	
	/**
	 * 관광정보 데이터 조회
	 * @param regionCode
	 * @return
	 */
	@RequestMapping(value = "/data", method = RequestMethod.GET)
    public ProgramListO getData(@RequestParam(value="regionCode") String regionCode) {
		List<TourInfo> list = service.getTourInfoByRegionCode(regionCode);
        return new ProgramListO(regionCode, list);
    }
	
	/**
	 * 생태 관광지 중에 서비스 지역 컬럼에서 특정 지역에서 진행되는 프로그램명과 테마를 반환
	 */
	@RequestMapping(value = "/program/list", method = RequestMethod.GET)
    public List<ProgramListO2> getProgramList(
    		@RequestParam(value="region") String region
    ) {
		List<ProgramListO2> resultList = new ArrayList<>();
		
		List<TourInfo> list = service.getTourInfoByResionName(region);
		for (int i = 0; i < list.size(); i++) {
			TourInfo ti = list.get(i);
			List<TourInfo> subList = new ArrayList<>();
			subList.add(ti);
			for (int j = i+1; j < list.size(); j++) {
				if(ti.getRegionCode().equals(list.get(j).getRegionCode())) {
					subList.add(list.get(j));
					list.remove(j--);
				}
			}
			resultList.add(new ProgramListO2(subList));
		}
		return resultList;
    }
	
	/**
	 * 생태 정보 데이터에 "프로그램 소개” 컬럼에서 특정 문자열이 포함된 레코드에서 서비스 지역 개수를 세어 반환
	 */
	@RequestMapping(value = "/program/count/region", method = RequestMethod.GET)
    public ProgramCountListO getProgramCountAsRegion(
    		@RequestParam(value="keyword") String keyword
    ) {
		List<TourInfo> list = service.getTourInfoSearchProgramDesc(keyword);
		
		Map<String, Integer> countMap = new HashMap<>();
		for (TourInfo tourInfo : list) {
			if(countMap.containsKey(tourInfo.getRegionString())) {
				countMap.put(tourInfo.getRegionString(), countMap.get(tourInfo.getRegionString())+1);
			} else {
				countMap.put(tourInfo.getRegionString(), 1);
			}
		}
		
		return new ProgramCountListO(keyword, countMap);
    }
	
	/**
	 * 모든 레코드에 프로그램 상세 정보를 읽어와서 입력 단어의 출현빈도수를 계산하여 반환
	 */
	@RequestMapping(value = "/program/count/word", method = RequestMethod.GET)
    public WordCountO getProgramCountAsWord(
    		@RequestParam(value="keyword") String keyword
    ) {
		int count = service.getWordCountFromProgramDetailDesc(keyword);
		
		return new WordCountO(keyword, count);
    }
	
	/**
	 * 지역명과 관광 키워드를 입력받아 프로그램 코드 반환
	 */
	@RequestMapping(value = "/recommand/program", method = RequestMethod.GET)
    public ResultMapO getRecommandProgram(
    		@RequestParam(value="keyword") String keyword
    ) {
		return new ResultMapO();
    }
	
	/**
	 * ID, PW 받아 내부 DB 에 계정 저장하고 토큰 생성하여 로그출력
	 * 패스워드는 인코딩하여 저장
	 * 토큰은 특정 secret 으로 서명하여 생성
	 */
	@RequestMapping(value = "/member", method = RequestMethod.POST)
    public ResultO addMember(
    		@RequestParam(value="id") String id
    		, @RequestParam(value="password") String password
    ) {
        return new ResultO();
    }
	
	/**
	 * 생성된 계정 (ID, PW)으로 로그인 요청하면 토큰 발급
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultMapO login(
    		@RequestParam(value="id") String id
    		, @RequestParam(value="password") String password
    ) {
		return new ResultMapO();
    }
	
	/**
	 * Authorization 헤더에 “Bearer Token”으로 입력 요청을 하면 토큰을 재발급
	 */
	@RequestMapping(value = "/token", method = RequestMethod.POST)
    public ResultMapO resetToken(
    		@RequestHeader(value="Authorization", required=true) String authorizationHeader
    ) {
		return new ResultMapO();
    }
	
}
