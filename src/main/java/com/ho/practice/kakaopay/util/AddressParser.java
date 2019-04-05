package com.ho.practice.kakaopay.util;

import com.ho.practice.kakaopay.model.Region;

public class AddressParser {

	public AddressParser() {
	}

	public Region parse(String address) {
		Region region = new Region();
		
		String[] tokens = address.split(" ");
		for (int i = 0; i < tokens.length; i++) {
			String name = tokens[i];
			switch (i) {
			case 0:
				// 시도
				if(name.length()-1 == name.indexOf("시")
					|| name.length()-1 == name.indexOf("도")) {
					region.setSidoName(name);
				} else {
					region.setDetailAddress(name);
				}
				break;
			case 1:
				// 시군구
				if(name.length()-1 == name.indexOf("시")
					|| name.length()-1 == name.indexOf("군")
					|| name.length()-1 == name.indexOf("구")) {
					region.setSggName(name);
				} else {
					region.setDetailAddress(name);
				}
				break;
			case 2:
				// 읍면동
				if(name.length()-1 == name.indexOf("읍")
					|| name.length()-1 == name.indexOf("면")
					|| name.length()-1 == name.indexOf("동")) {
					region.setSggName(name);
				} else {
					region.setDetailAddress(name);
				}
				break;
			case 3:
				// 상세
				region.setDetailAddress(name);
				break;
			default:
				break;
			}
		}
		
		return region;
	}
}
