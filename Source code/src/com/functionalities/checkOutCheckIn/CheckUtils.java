package com.functionalities.checkOutCheckIn;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.connection.DBConnection;
import com.exceptions.ConnectionErrorException;
import com.exceptions.QueryExecutionException;

public class CheckUtils {
	private static Connection connection;
	public static boolean isCardNoValid(int card_no) throws ConnectionErrorException, QueryExecutionException, SQLException {
		boolean isValidMember = false;
		String queryString = "select card_no from borrower where card_no="+card_no+";";
		connection = DBConnection.getConnection();
		ResultSet resultSet = DBConnection.executeSQLQuery(connection, queryString);
		if(resultSet != null) {
			if(resultSet.next() == false){
				isValidMember = false;
			} else {
				isValidMember = true;
			}
		}
		return isValidMember;
	}

	public static boolean isBookIdValid(String book_id) throws ConnectionErrorException, QueryExecutionException, SQLException {
		boolean isValidBookId = false;
		String queryString = "select book_id from book where book_id='"+book_id+"';";
		connection = DBConnection.getConnection();
		ResultSet resultSet = DBConnection.executeSQLQuery(connection, queryString);
		if(resultSet != null) {
			if(resultSet.next() == false){
				isValidBookId = false;
			} else {
				isValidBookId = true;
			}
		}
		resultSet.close();
		return isValidBookId;
	}
	
	public static boolean isBookCheckedInByMember(String book_id, int card_no) throws ConnectionErrorException, QueryExecutionException, SQLException {
		boolean isBookCheckedIn = false;
		String queryString = "select book_id from book_loans where book_id ='"+book_id+"' and card_no="+card_no+" and date_in is null;";
		connection = DBConnection.getConnection();
		ResultSet resultSet = DBConnection.executeSQLQuery(connection, queryString);
		if(resultSet != null) {
			if(resultSet.next() == false){
				isBookCheckedIn = false;
			} else {
				isBookCheckedIn = true;
			}
		}
		resultSet.close();
		return isBookCheckedIn;
	}
	
	public static boolean isValidName(String name) throws ConnectionErrorException, QueryExecutionException, SQLException {
		boolean isValidName = false;
		String queryString = "select card_no,fname,lname from borrower where fname like '%"+name+"%' or lname like '%"+name+"%';";
		connection = DBConnection.getConnection();
		ResultSet resultSet = DBConnection.executeSQLQuery(connection, queryString);
		if(resultSet != null) {
			if(resultSet.next() == false){
				isValidName = false;
			} else {
				isValidName = true;
			}
		}
		resultSet.close();
		return isValidName;
	}
	
	public static ArrayList<Integer> getCardNumberListFromName(String name) throws ConnectionErrorException, QueryExecutionException, SQLException {
		ArrayList<Integer> cardNumberList = new ArrayList<Integer>();
		String queryString = "select card_no from borrower where fname like '%"+name+"%' or lname like '%"+name+"%';";
		//System.out.println(queryString);
		connection = DBConnection.getConnection();
		ResultSet resultSet = DBConnection.executeSQLQuery(connection, queryString);
		if(resultSet != null) {
			while(resultSet.next()){
				int card_no = resultSet.getInt("card_no");
				cardNumberList.add(card_no);
			}
		}
		resultSet.close();
		return cardNumberList;
	}

	public static int findLoanIdSequence() throws ConnectionErrorException, QueryExecutionException, SQLException {
		int newLoanId = 0;
		String queryString = "select max(loan_id) as sequence from book_loans;";
		connection = DBConnection.getConnection();
		ResultSet resultSet = DBConnection.executeSQLQuery(connection, queryString);
		if(resultSet != null) {
			while(resultSet.next()){
				newLoanId = resultSet.getInt("sequence");
				//System.out.println("loanId->"+newLoanId);
			}
		}
		resultSet.close();
		return newLoanId + 1;
	}
	
	public static boolean isValidNameForCard(int card_no, String name) throws ConnectionErrorException, QueryExecutionException, SQLException {
		boolean isValidName = false;
		String queryString = "select card_no,fname,lname from borrower where card_no="+card_no+" and (fname like '%"+name+"%' or lname like '%"+name+"%');";
		connection = DBConnection.getConnection();
		ResultSet resultSet = DBConnection.executeSQLQuery(connection, queryString);
		if(resultSet != null) {
			if(resultSet.next() == false){
				isValidName = false;
			} else {
				isValidName = true;
			}
		}
		resultSet.close();
		return isValidName;
	}
}
