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
				// �õ�
				if(name.length()-1 == name.indexOf("��")
					|| name.length()-1 == name.indexOf("��")) {
					region.setSidoName(name);
				} else {
					region.setDetailAddress(name);
				}
				break;
			case 1:
				// �ñ���
				if(name.length()-1 == name.indexOf("��")
					|| name.length()-1 == name.indexOf("��")
					|| name.length()-1 == name.indexOf("��")) {
					region.setSggName(name);
				} else {
					region.setDetailAddress(name);
				}
				break;
			case 2:
				// ���鵿
				if(name.length()-1 == name.indexOf("��")
					|| name.length()-1 == name.indexOf("��")
					|| name.length()-1 == name.indexOf("��")) {
					region.setSggName(name);
				} else {
					region.setDetailAddress(name);
				}
				break;
			case 3:
				// ��
				region.setDetailAddress(name);
				break;
			default:
				break;
			}
		}
		
		return region;
	}
}
