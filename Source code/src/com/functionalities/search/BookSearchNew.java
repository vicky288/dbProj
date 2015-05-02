package com.functionalities.search;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.connection.DBConnection;
import com.exceptions.ConnectionErrorException;
import com.exceptions.QueryExecutionException;

public class BookSearchNew {
	private static Connection connection;
	public static ArrayList<SearchResult_New> searchExtended(String book_id, String title, String fName, String mName, String lName) throws ConnectionErrorException, QueryExecutionException, SQLException{
		connection = DBConnection.getConnection();
		ArrayList<SearchResult_New> searchResultList = new ArrayList<SearchResult_New>();
		if (book_id.equals("")) {
			book_id = null;
		}
		if (title.equals("")) {
			title = null;
		} else if(title.contains("'")) {			// to deal with titles like "Rick Steve's Italy 2001"
			title = title.replaceAll("'", "''");
		}
		if (fName.equals("")) {
			fName = null;
		}
		if (mName.equals("")) {
			mName = null;
		}
		if (lName.equals("")) {
			lName = null;
		}
		ArrayList<String> bookIds = findBookIdsFromMultiParameter_extended(book_id, title, fName, mName, lName);
		if (bookIds != null) {
			searchResultList = BookSearch.getSearchResultListFromBookIdList(bookIds);	
			DBConnection.closeConnection(connection);
			return searchResultList;
		} else {
			DBConnection.closeConnection(connection);
			return null;
		}
	}
	public static ArrayList<String> findBookIdsFromMultiParameter_extended(String book_id, String title, String fName, String mName, String lName) throws ConnectionErrorException, QueryExecutionException, SQLException {
		String queryString = null;
		if (book_id == null && title ==null && fName == null && mName == null && lName ==null) {	
			return null;
		}
		if (book_id == null && title ==null && fName == null && mName == null && lName !=null) {	
			queryString = "SELECT book_id from book_authors where lname like '%"+lName+"%' ;";
		}
		if (book_id == null && title ==null && fName == null && mName != null && lName ==null) {	
			queryString = "SELECT book_id from book_authors where mname like '%"+mName+"%' ;";
		}
		if (book_id == null && title ==null && fName == null && mName != null && lName !=null) {
			queryString = "SELECT book_id from book_authors where mname like '%"+mName+"%' and lname like '%"+lName+"%' ;";
		}
		if (book_id == null && title ==null && fName != null && mName == null && lName ==null) {	
			queryString = "SELECT book_id from book_authors where fname like '%"+fName+"%' ;";
		}
		if (book_id == null && title ==null && fName != null && mName == null && lName !=null) {			
			queryString = "SELECT book_id from book_authors where fname like '%"+fName+"%' and lname like '%"+lName+"%' ;";
		}
		if (book_id == null && title ==null && fName != null && mName != null && lName ==null) {			
			queryString = "SELECT book_id from book_authors where fname like '%"+fName+"%' and mname like '%"+mName+"%' ;";
		}
		if (book_id == null && title ==null && fName != null && mName != null && lName !=null) {	
			queryString = "SELECT book_id from book_authors where fname like '%"+fName+"%' and mname like '%"+mName+"%' and lname like '%"+lName+"%' ;";
		}
		if (book_id == null && title !=null && fName == null && mName == null && lName ==null) {
			queryString = "SELECT book_id from book where title like '%"+title+"%' ;";
		}
		if (book_id == null && title !=null && fName == null && mName == null && lName !=null) {	
			queryString = "SELECT book.book_id from book, book_authors where title like '%"+title+"%' and lname like '%"+lName+"%' and book.book_id = book_authors.book_id;";
		}
		if (book_id == null && title !=null && fName == null && mName != null && lName ==null) {			
			queryString = "SELECT book.book_id from book, book_authors where title like '%"+title+"%' and mname like '%"+mName+"%' and book.book_id = book_authors.book_id;";
		}
		if (book_id == null && title !=null && fName == null && mName != null && lName !=null) {			
			queryString = "SELECT book.book_id from book, book_authors where title like '%"+title+"%' and mname like '%"+mName+"%' and lname like '%"+lName+"%' and book.book_id = book_authors.book_id;";
		}
		if (book_id == null && title !=null && fName != null && mName == null && lName ==null) {			
			queryString = "SELECT book.book_id from book, book_authors where title like '%"+title+"%' and fname like '%"+fName+"%' and book.book_id = book_authors.book_id;";
		}
		if (book_id == null && title !=null && fName != null && mName == null && lName !=null) {			
			queryString = "SELECT book.book_id from book, book_authors where title like '%"+title+"%' and fname like '%"+fName+"%' and lname like '%"+lName+"%' and book.book_id = book_authors.book_id;";
		}
		if (book_id == null && title !=null && fName != null && mName != null && lName ==null) {			
			queryString = "SELECT book.book_id from book, book_authors where title like '%"+title+"%' and fname like '%"+fName+"%' and mname like '%"+mName+"%' and book.book_id = book_authors.book_id;";
		}
		if (book_id == null && title !=null && fName != null && mName != null && lName !=null) {			
			queryString = "SELECT book.book_id from book, book_authors where title like '%"+title+"%' and fname like '%"+fName+"%' and mname like '%"+mName+"%' and lname like '%"+lName+"%' and book.book_id = book_authors.book_id;";
		}
		if (book_id != null && title ==null && fName == null && mName == null && lName ==null) {			
			queryString = "SELECT book_id from book where book_id like '%"+book_id+"%' ;";
		}
		if (book_id != null && title ==null && fName == null && mName == null && lName !=null) {	
			queryString = "SELECT book.book_id from book, book_authors where book.book_id like '%"+book_id+"%' and lname like '%"+lName+"%' and book.book_id = book_authors.book_id;";
		}
		if (book_id != null && title ==null && fName == null && mName != null && lName ==null) {	
			queryString = "SELECT book.book_id from book, book_authors where book.book_id like '%"+book_id+"%' and mname like '%"+mName+"%' and book.book_id = book_authors.book_id;";
		}
		if (book_id != null && title ==null && fName == null && mName != null && lName !=null) {			
			queryString = "SELECT book.book_id from book, book_authors where book.book_id like '%"+book_id+"%' and mname like '%"+mName+"%' and lname like '%"+lName+"%' and book.book_id = book_authors.book_id;";
		}
		if (book_id != null && title ==null && fName != null && mName == null && lName ==null) {			
			queryString = "SELECT book.book_id from book, book_authors where book.book_id like '%"+book_id+"%' and fname like '%"+fName+"%' and book.book_id = book_authors.book_id;";
		}
		if (book_id != null && title ==null && fName != null && mName == null && lName !=null) {			
			queryString = "SELECT book.book_id from book, book_authors where book.book_id like '%"+book_id+"%' and fname like '%"+fName+"%' and lname like '%"+lName+"%' and book.book_id = book_authors.book_id;";
		}
		if (book_id != null && title ==null && fName != null && mName != null && lName ==null) {			
			queryString = "SELECT book.book_id from book, book_authors where book.book_id like '%"+book_id+"%' and fname like '%"+fName+"%' and mname like '%"+mName+"%' and book.book_id = book_authors.book_id;";
		}
		if (book_id != null && title ==null && fName != null && mName != null && lName !=null) {			
			queryString = "SELECT book.book_id from book, book_authors where book.book_id like '%"+book_id+"%' and fname like '%"+fName+"%' and mname like '%"+mName+"%' and lname like '%"+lName+"%' and book.book_id = book_authors.book_id;";
		}
		if (book_id != null && title !=null && fName == null && mName == null && lName ==null) {			
			queryString = "SELECT book.book_id from book, book_authors where book.book_id like '%"+book_id+"%' and title like '%"+title+"%' and book.book_id = book_authors.book_id;";
		}
		if (book_id != null && title !=null && fName == null && mName == null && lName !=null) {			
			queryString = "SELECT book.book_id from book, book_authors where book.book_id like '%"+book_id+"%' and title like '%"+title+"%' and lname like '%"+lName+"%' and book.book_id = book_authors.book_id;";
		}
		if (book_id != null && title !=null && fName == null && mName != null && lName ==null) {			
			queryString = "SELECT book.book_id from book, book_authors where book.book_id like '%"+book_id+"%' and title like '%"+title+"%' and mname like '%"+mName+"%' and book.book_id = book_authors.book_id;";
		}
		if (book_id != null && title !=null && fName == null && mName != null && lName !=null) {			
			queryString = "SELECT book.book_id from book, book_authors where book.book_id like '%"+book_id+"%' and title like '%"+title+"%' and mname like '%"+mName+"%' and lname like '%"+lName+"%' and book.book_id = book_authors.book_id;";
		}
		if (book_id != null && title !=null && fName != null && mName == null && lName ==null) {			
			queryString = "SELECT book.book_id from book, book_authors where book.book_id like '%"+book_id+"%' and title like '%"+title+"%' and fname like '%"+fName+"%' and book.book_id = book_authors.book_id;";
		}
		if (book_id != null && title !=null && fName != null && mName == null && lName !=null) {			
			queryString = "SELECT book.book_id from book, book_authors where book.book_id like '%"+book_id+"%' and title like '%"+title+"%' and fname like '%"+fName+"%' and lname like '%"+lName+"%' and book.book_id = book_authors.book_id;";
		}
		if (book_id != null && title !=null && fName != null && mName != null && lName ==null) {			
			queryString = "SELECT book.book_id from book, book_authors where book.book_id like '%"+book_id+"%' and title like '%"+title+"%' and fname like '%"+fName+"%' and mname like '%"+mName+"%' and book.book_id = book_authors.book_id;";
		}
		if (book_id != null && title !=null && fName != null && mName != null && lName !=null) {			
			queryString = "SELECT book.book_id from book, book_authors where book.book_id like '%"+book_id+"%' and title like '%"+title+"%' and  fname like '%"+fName+"%' and mname like '%"+mName+"%' and lname like '%"+lName+"%' and book.book_id = book_authors.book_id;";

		}	
		System.out.println(queryString);
		return BookSearch.getBookIdsResultListFromQueryString(queryString);
	}

}
