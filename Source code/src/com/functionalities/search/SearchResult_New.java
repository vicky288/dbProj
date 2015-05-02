package com.functionalities.search;

import java.util.HashMap;

public class SearchResult_New {
	private String book_id;
	private String title;
	private String authors;
	private HashMap<Integer,BranchAvailability> branchInfo;
	public String getBook_id() {
		return book_id;
	}
	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthors() {
		return authors;
	}
	public void setAuthors(String authors) {
		this.authors = authors;
	}
	public HashMap<Integer, BranchAvailability> getBranchInfo() {
		return branchInfo;
	}
	public void setBranchInfo(HashMap<Integer, BranchAvailability> branchInfo) {
		this.branchInfo = branchInfo;
	}

}
