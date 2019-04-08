package com.ho.practice.kakaopay.vo.output;

public class WordCountO {

	private String keyword;
	private int count;
	
	public WordCountO() {
	}
	public WordCountO(String keyword, int count) {
		this.keyword = keyword;
		this.count = count;
	}
	public String getKeyword() {
		return keyword;
	}
	public int getCount() {
		return count;
	}
	
}
