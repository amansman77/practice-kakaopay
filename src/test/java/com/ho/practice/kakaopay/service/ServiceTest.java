package com.ho.practice.kakaopay.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.ho.practice.kakaopay.dto.TourInfo;
import com.ho.practice.kakaopay.repository.ProgramRepository;
import com.ho.practice.kakaopay.repository.ProgramThemeRepository;
import com.ho.practice.kakaopay.repository.RegionRepository;
import com.ho.practice.kakaopay.repository.ServiceRegionRepository;
import com.ho.practice.kakaopay.repository.ThemeRepository;
import com.ho.practice.kakaopay.service.impl.ServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ServiceImpl.class})
public class ServiceTest {
	
	@Autowired
    private ServiceImpl serviceImpl;

	@MockBean
    private ProgramRepository programRepository;;
    @MockBean
	ThemeRepository themeRepository;
    @MockBean
	RegionRepository regionRepository;
    @MockBean
	ProgramThemeRepository programThemeRepository;
    @MockBean
	ServiceRegionRepository serviceRegionRepository;
    
//	@Test
    public void testGetTourInfoByRegionCode() throws Exception {
    	List<TourInfo> list = serviceImpl.getTourInfoByRegionCode("reg0889");
        assertThat(list.size()).isEqualTo(4);
    }
	
}
