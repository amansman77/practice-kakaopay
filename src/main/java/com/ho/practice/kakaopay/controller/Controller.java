package com.ho.practice.kakaopay.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ho.practice.kakaopay.vo.input.DataInsertI;
import com.ho.practice.kakaopay.vo.input.DataUpdateI;
import com.ho.practice.kakaopay.vo.output.ProgramListO;
import com.ho.practice.kakaopay.vo.output.ProgramListO2;
import com.ho.practice.kakaopay.vo.output.ResultMapO;
import com.ho.practice.kakaopay.vo.output.ResultO;

@RestController
public class Controller {
	
	@RequestMapping(value = "/init/database", method = RequestMethod.POST)
    public ResultO greeting() {
        return new ResultO();
    }
	
	@RequestMapping(value = "/data", method = RequestMethod.POST)
    public ResultO addData(@RequestBody(required=true) DataInsertI dataInsertI) {
		return new ResultO();
    }
	
	@RequestMapping(value = "/data", method = RequestMethod.PUT)
    public ResultO updateData(@RequestBody(required=true) DataUpdateI dataUpdateI) {
        return new ResultO();
    }
	
	@RequestMapping(value = "/data", method = RequestMethod.GET)
    public ProgramListO getData(@RequestParam(value="regionId") String regionId) {
        return new ProgramListO();
    }
	
	/**
	 * 생태 관광지 중에 서비스 지역 컬럼에서 특정 지역에서 진행되는 프로그램명과 테마를 반환
	 */
	@RequestMapping(value = "/program/list", method = RequestMethod.GET)
    public ProgramListO2 getProgramList(
    		@RequestParam(value="region") String region
    ) {
		return new ProgramListO2();
    }
	
	/**
	 * 생태 정보 데이터에 "프로그램 소개” 컬럼에서 특정 문자열이 포함된 레코드에서 서비스 지역 개수를 세어 반환
	 */
	@RequestMapping(value = "/program/count", method = RequestMethod.GET)
    public ResultMapO getProgramCount(
    		@RequestParam(value="keyword") String keyword
    ) {
		return new ResultMapO();
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
