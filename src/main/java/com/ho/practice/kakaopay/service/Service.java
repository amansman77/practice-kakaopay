package com.ho.practice.kakaopay.service;

import java.util.List;

import com.ho.practice.kakaopay.dto.TourInfo;

public interface Service {

	void saveTourInfo(String programCode, String programName, String programDesc, String programDetailDesc
			, String themeString, String regionString) throws Exception;

	List<TourInfo> getTourInfo(String regionCode);

}
