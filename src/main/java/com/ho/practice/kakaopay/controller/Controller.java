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
	 * ���� ������ �߿� ���� ���� �÷����� Ư�� �������� ����Ǵ� ���α׷���� �׸��� ��ȯ
	 */
	@RequestMapping(value = "/program/list", method = RequestMethod.GET)
    public ProgramListO2 getProgramList(
    		@RequestParam(value="region") String region
    ) {
		return new ProgramListO2();
    }
	
	/**
	 * ���� ���� �����Ϳ� "���α׷� �Ұ��� �÷����� Ư�� ���ڿ��� ���Ե� ���ڵ忡�� ���� ���� ������ ���� ��ȯ
	 */
	@RequestMapping(value = "/program/count", method = RequestMethod.GET)
    public ResultMapO getProgramCount(
    		@RequestParam(value="keyword") String keyword
    ) {
		return new ResultMapO();
    }
	
	/**
	 * ������� ���� Ű���带 �Է¹޾� ���α׷� �ڵ� ��ȯ
	 */
	@RequestMapping(value = "/recommand/program", method = RequestMethod.GET)
    public ResultMapO getRecommandProgram(
    		@RequestParam(value="keyword") String keyword
    ) {
		return new ResultMapO();
    }
	
	/**
	 * ID, PW �޾� ���� DB �� ���� �����ϰ� ��ū �����Ͽ� �α����
	 * �н������ ���ڵ��Ͽ� ����
	 * ��ū�� Ư�� secret ���� �����Ͽ� ����
	 */
	@RequestMapping(value = "/member", method = RequestMethod.POST)
    public ResultO addMember(
    		@RequestParam(value="id") String id
    		, @RequestParam(value="password") String password
    ) {
        return new ResultO();
    }
	
	/**
	 * ������ ���� (ID, PW)���� �α��� ��û�ϸ� ��ū �߱�
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultMapO login(
    		@RequestParam(value="id") String id
    		, @RequestParam(value="password") String password
    ) {
		return new ResultMapO();
    }
	
	/**
	 * Authorization ����� ��Bearer Token������ �Է� ��û�� �ϸ� ��ū�� ��߱�
	 */
	@RequestMapping(value = "/token", method = RequestMethod.POST)
    public ResultMapO resetToken(
    		@RequestHeader(value="Authorization", required=true) String authorizationHeader
    ) {
		return new ResultMapO();
    }
	
}
