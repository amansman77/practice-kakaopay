package com.ho.practice.kakaopay.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;

public class CsvParser {

	private BufferedReader br;
	private int columnSize;
	private int pos = 1;
	
	public CsvParser(URI uri, int columnSize) throws FileNotFoundException, UnsupportedEncodingException {
		FileInputStream fi = new FileInputStream(uri.getPath());
		InputStreamReader isr = new InputStreamReader(fi, "euc-kr");
		br = new BufferedReader(isr);
		this.columnSize = columnSize;
	}

	public CsvParser(String path, int columnSize) throws FileNotFoundException, UnsupportedEncodingException {
		FileInputStream fi = new FileInputStream(path);
		InputStreamReader isr = new InputStreamReader(fi, "euc-kr");
		br = new BufferedReader(isr);
		this.columnSize = columnSize;
	}

	public CsvParser(InputStream inputStream, int columnSize) throws UnsupportedEncodingException {
		InputStreamReader isr = new InputStreamReader(inputStream, "euc-kr");
		br = new BufferedReader(isr);
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
				// 연속 데이터 유지
				isCurrentData = !isCurrentData;
			} else if (!isCurrentData && c == '\n') {
				// 줄바꿈 제거
				continue;
	        } else if (!isCurrentData && c == ',') {
	        	// 데이터 구분
				arr[columnIdx++] = curData.toString();
				curData = new StringBuffer();
	        } else if((!isCurrentData && c == '\r')
					|| columnIdx == columnSize) {
	        	// 데이터의 끝
				arr[columnIdx++] = curData.toString();
				curData = new StringBuffer();
				break;
			} else {
	        	curData.append(c);
	        }
		}
		
		return arr;
	}
    
}
