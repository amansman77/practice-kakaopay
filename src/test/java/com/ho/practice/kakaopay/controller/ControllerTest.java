package com.ho.practice.kakaopay.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.ho.practice.kakaopay.vo.input.DataInsertI;
import com.ho.practice.kakaopay.vo.input.DataUpdateI;
import com.ho.practice.kakaopay.vo.output.ProgramCountListO;
import com.ho.practice.kakaopay.vo.output.ProgramListO;
import com.ho.practice.kakaopay.vo.output.RegionCountO;
import com.ho.practice.kakaopay.vo.output.ResultO;
import com.ho.practice.kakaopay.vo.output.WordCountO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTest {
	@Autowired
    private TestRestTemplate restTemplate;

//	@Test
    public void testInitDatabase() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json; charset=utf-8");     
        headers.set("Accept", "application/json; charset=utf-8");     
 
        ResponseEntity<ResultO> response = this.restTemplate.postForEntity("/init/database", headers, ResultO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getResult()).isEqualTo("true");
    }
	
//    @Test
    public void testAddData() throws Exception {
    	HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json; charset=utf-8");     
        headers.set("Accept", "application/json; charset=utf-8");     
 
        DataInsertI dataInsertI = new DataInsertI("ho program", "설명", "상세설명", "건강나누리캠프,자연생태체험", "경상북도 경주시");
        
        HttpEntity<DataInsertI> request = new HttpEntity<>(dataInsertI, headers);
        
        ResponseEntity<ResultO> response = this.restTemplate.postForEntity("/data", request, ResultO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getResult()).isEqualTo("true");
    }
    
//    @Test
    public void testUpdateData() throws Exception {
    	HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json; charset=utf-8");     
        headers.set("Accept", "application/json; charset=utf-8");     
 
        DataUpdateI dataUpdateI = new DataUpdateI("prg2341", "ho program1", "설명1", "상세설명1", "자연생태체험,해안생태,아동·청소년 체험학습", "전라남도 영암군 영암읍 천황사로 280-43 월출산국립공원 사무소");
        
        HttpEntity<DataUpdateI> request = new HttpEntity<>(dataUpdateI, headers);
        
        ResponseEntity<ResultO> response = this.restTemplate.exchange("/data", HttpMethod.PUT, request, ResultO.class, new HashMap<String, String>());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getResult()).isEqualTo("true");
    }
    
    @Test
    public void testGetData() {
        ResponseEntity<ProgramListO> response = restTemplate.getForEntity("/data?regionCode=reg0889", ProgramListO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getRegion_id()).isEqualTo("reg0889");
        assertThat(response.getBody().getPrograms().length).isEqualTo(4);
    }
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
    public void testGetProgramList() {
        ResponseEntity<ArrayList> response = restTemplate.getForEntity("/program/list?region=평창군", ArrayList.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(2);
    }
    
    @Test
    public void testGetProgramCountAsRegion() {
        ResponseEntity<ProgramCountListO> response = restTemplate.getForEntity("/program/count/region?keyword=세계문화유산", ProgramCountListO.class);
        RegionCountO[] regionCounts = response.getBody().getPrograms();
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getKeyword()).isEqualTo("세계문화유산");
        assertThat(regionCounts[0].getRegion()).isEqualTo("경상북도 경주시");
        assertThat(regionCounts[0].getCount()).isEqualTo(2);
    }
    
    @Test
    public void testGetProgramCountAsWord() {
        ResponseEntity<WordCountO> response = restTemplate.getForEntity("/program/count/word?keyword=문화", WordCountO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getKeyword()).isEqualTo("문화");
        assertThat(response.getBody().getCount()).isEqualTo(59);
    }
    
}
