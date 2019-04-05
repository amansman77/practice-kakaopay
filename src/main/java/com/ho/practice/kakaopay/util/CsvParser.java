package com.ho.practice.kakaopay.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;

public class CsvParser {

	private BufferedReader br;
	private int columnSize;
	private int pos = 1;
	
	public CsvParser(URI uri, int columnSize) throws FileNotFoundException {
		FileReader fr = new FileReader(new File(uri));
		br = new BufferedReader(fr);
		this.columnSize = columnSize;
	}

	public boolean hasNext() throws IOException {
		boolean hasNext = false;
		br.mark(pos);
		if(br.read() != -1) {
			hasNext = true;
		}
		br.reset();
		return hasNext;
	}

	public String[] next() throws IOException {
		String[] arr = new String[columnSize];
		
		int columnIdx = 0;
		StringBuffer curData = new StringBuffer();
		boolean isCurrentData = false;
		while (br.ready()) {
			char c = (char) br.read();
			pos++;
			if(c == '"') {
				// ���� ������ ����
				isCurrentData = !isCurrentData;
			} else if (!isCurrentData && c == '\n') {
				// �ٹٲ� ����
				continue;
	        } else if (!isCurrentData && c == ',') {
				// ������ ����
				arr[columnIdx++] = String.valueOf(curData).trim();
				curData = new StringBuffer();
	        } else if((!isCurrentData && c == '\r')
					|| columnIdx == columnSize) {
				// �������� ��
				arr[columnIdx++] = String.valueOf(curData).trim();
				curData = new StringBuffer();
				break;
			} else {
	        	curData.append(c);
	        }
		}
		
		return arr;
	}
    
}
