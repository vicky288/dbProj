package com.functionalities.search;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.connection.DBConnection;
import com.exceptions.ConnectionErrorException;
import com.exceptions.QueryExecutionException;
//don't call functions null as parameter pass ""
public class BookSearch {
	private static Connection connection;
	public static ArrayList<SearchResult_New> search(String book_id, String title, String author_name) throws ConnectionErrorException, QueryExecutionException, SQLException{
		connection = DBConnection.getConnection();
		ArrayList<SearchResult_New> searchResultList = new ArrayList<SearchResult_New>();
		String query = null;
		//if any input is empty string set it null
		if (book_id.equals("")) {
			book_id = null;
		}
		if (title.equals("")) {
			title = null;
		} else if(title.contains("'")) {			// to deal with titles like "Rick Steve's Italy 2001"
			title = title.replaceAll("'", "''");
		}
		if (author_name.equals("")) {
			author_name = null;
		}
		if(connection.isClosed()) {
			System.out.println("Why is it closed...............");
		}
		ArrayList<String> bookIds = findBookIdsFromMultiParameter(book_id, title, author_name);
		if (bookIds != null) {
			searchResultList = getSearchResultListFromBookIdList(bookIds);	
			DBConnection.closeConnection(connection);
			return searchResultList;
		} else {
			DBConnection.closeConnection(connection);
			return null;
		}
		
	}
	
	public static ArrayList<String> findBookIdsFromMultiParameter(String book_id, String title, String author_name) throws ConnectionErrorException, QueryExecutionException, SQLException{
		String queryString = null;

		//case 1
		if (book_id != null && title ==null && author_name == null ) {
			queryString = "SELECT book_id from book where book_id like '%"+book_id+"%' ;";
		}

		//case 2
		if (book_id == null && title !=null && author_name == null ) {
			queryString = "SELECT book_id from book where title like '%"+title+"%' ;";
		}

		//case 3
		if (book_id == null && title ==null && author_name != null ) {
			queryString = "SELECT book_id from book_authors where author_name like '%"+author_name+"%' ;";
		}

		//case 4
		if (book_id != null && title !=null && author_name == null ) {
			queryString = "SELECT book_id, title from book where title like '%"+title+"%' and book_id like '%"+book_id+"%';";
		}

		//case 5
		if (book_id == null && title !=null && author_name != null ) {
			queryString = "SELECT book.book_id from book, book_authors where title like '%"+title+"%' and author_name like '%"+author_name+"%' and book.book_id = book_authors.book_id;";
			//System.out.println(queryString);
		}

		//case 6
		if (book_id != null && title ==null && author_name != null ) {
			queryString = "SELECT book.book_id from book, book_authors where book.book_id like '%"+book_id+"%' and author_name like '%"+author_name+"%' and book.book_id = book_authors.book_id;";
		}

		//case 7
		if (book_id != null && title !=null && author_name != null ) {
			queryString = "SELECT book.book_id from book, book_authors where book.book_id like '%"+book_id+"%' and title like '%"+title+"%' and author_name like '%"+author_name+"%' and book.book_id = book_authors.book_id;";
		}
		//case 8
		if (book_id == null && title ==null && author_name == null ) {
			return null;
		}
		System.out.println(queryString);
		return getBookIdsResultListFromQueryString(queryString);
	}

	public static HashMap<String, String> findAuthorsFromBookIds(ArrayList<String> bookIds) throws ConnectionErrorException, QueryExecutionException, SQLException{
		HashMap<String, String> authorsMap = new HashMap<String, String>();
		connection = DBConnection.getConnection();
		for(String id:bookIds) {
			String authorNames = "";
			String queryString = "SELECT author_name FROM book_authors WHERE book_id ='"+id+"'";
			ResultSet resultSet = DBConnection.executeSQLQuery(connection, queryString);
			if(resultSet != null) {
				//System.out.println("Got it");
				while(resultSet.next()){
					String authorName = resultSet.getString("author_name");
					authorNames = authorName + ", " + authorNames;
				}
			}

			authorNames = authorNames.substring(0, authorNames.length()-2);
			authorsMap.put(id, authorNames);
		}
		return authorsMap;
	}


	public static String findAuthorsFromID(String book_id){

		return null;
	}

	public static ArrayList<String> getBookIdsResultListFromQueryString(String queryString) throws ConnectionErrorException, QueryExecutionException, SQLException{
		ArrayList<String> bookIds = new ArrayList<String>();
		connection = DBConnection.getConnection();
		ResultSet resultSet = DBConnection.executeSQLQuery(connection, queryString);
		if(resultSet != null) {
			//System.out.println("Got it");
			while(resultSet.next()){
				String id = resultSet.getString("book_id");
				//System.out.println(id);
				bookIds.add(id);
			}
		}
		return bookIds;
	}
	public static ArrayList<SearchResult_New> getSearchResultListFromBookIdList(ArrayList<String> bookIds) throws ConnectionErrorException, QueryExecutionException, SQLException{
		ArrayList<SearchResult_New> searchResultList = new ArrayList<SearchResult_New>();

		HashMap<String, String> bookAuthorsMap = findAuthorsFromBookIds(bookIds);
		HashMap<String, SearchResult_New> searchResultMap = setSearchResultByBookId(bookIds);
		for (String bookId : bookIds) {
			SearchResult_New searchResultObject = searchResultMap.get(bookId);
			String authorList = bookAuthorsMap.get(bookId);
			searchResultObject.setAuthors(authorList);
			searchResultObject.setBook_id(bookId);

			// Add the object to return List
			searchResultList.add(searchResultObject);
		}


		return searchResultList;
	}
	public static HashMap<String, SearchResult_New> setSearchResultByBookId(ArrayList<String> bookIds) throws ConnectionErrorException, QueryExecutionException, SQLException {
		HashMap<String, SearchResult_New> searchResultMap = new HashMap<String, SearchResult_New>();
		connection = DBConnection.getConnection();
		for(String id:bookIds) {
			SearchResult_New searchResultNew = new SearchResult_New();
			HashMap<Integer, BranchAvailability> branchInfo = new HashMap<Integer, BranchAvailability>();
			String queryString = "select book.title, book_copies.branch_id, book_copies.no_of_copies, book_copies.available_copies" + 
					" from book, book_copies where book.book_id = book_copies.book_id and book.book_id = '"+id+"'";
			ResultSet resultSet = DBConnection.executeSQLQuery(connection, queryString);
			if(resultSet != null) {
				while(resultSet.next()){
					BranchAvailability branchAvailability = new BranchAvailability();
					branchAvailability.setBranch_id(resultSet.getInt("branch_id"));
					branchAvailability.setNo_of_copies(resultSet.getInt("no_of_copies"));
					branchAvailability.setAvailable_copies(resultSet.getInt("available_copies"));

					branchInfo.put(resultSet.getInt("branch_id"), branchAvailability);

					searchResultNew.setTitle(resultSet.getString("title"));
				}
			}

			searchResultNew.setBranchInfo(branchInfo);
			//System.out.println(searchResult.getTitle()+" "+searchResult.getBranch_id() +" "+searchResult.getNo_of_copies()+" "+searchResult.getAvailable_copies());
			searchResultMap.put(id, searchResultNew);
		}
		return searchResultMap;
	}

	public static void main(String[] args) {
		try{ 
			//ArrayList<SearchResult_New> searchResultList = search("", "system", "bernd"); 
			ArrayList<SearchResult_New> searchResultList = search("", "", ""); 
			if(searchResultList != null) {
				System.out.println(searchResultList.size());
				for(SearchResult_New searchResult:searchResultList ){
					System.out.println("Entered");
					HashMap<Integer,BranchAvailability> branchInfo =searchResult.getBranchInfo();
					Set<Integer> branchIDSet =  branchInfo.keySet();
					Iterator<Integer> iterator = branchIDSet.iterator();
					while(iterator.hasNext()) {
						BranchAvailability branchAvailability = branchInfo.get(iterator.next());
						System.out.println(searchResult.getBook_id()+"#"+searchResult.getTitle()+"***"+searchResult.getAuthors()+"***"+branchAvailability.getBranch_id() +"~~"+branchAvailability.getNo_of_copies()+"~~"+branchAvailability.getAvailable_copies());
					}
				}
				
			} else {
				System.out.println("Thenga");
			}
		} catch (Exception e){
			e.printStackTrace();
			System.out.println("Exception");
		}

	}
}