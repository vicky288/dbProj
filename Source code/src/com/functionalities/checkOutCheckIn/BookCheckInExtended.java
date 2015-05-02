package com.functionalities.checkOutCheckIn;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.connection.DBConnection;
import com.exceptions.CardNumberNotValidException;
import com.exceptions.CheckInDateNotValidException;
import com.exceptions.ConnectionErrorException;
import com.exceptions.QueryExecutionException;
import com.functionalities.fines.Fines;

public class BookCheckInExtended {
	private static Connection connection;

	public static void checkIn_Extended(int loan_id, int card_no, String book_id, int branch_id, String date_in_String) throws ConnectionErrorException, QueryExecutionException, SQLException, ParseException, CheckInDateNotValidException {

		//crete the database connection
		connection = DBConnection.getConnection();

		//calculating check-in date
		java.sql.Date date_in = null;
		if(date_in_String.equals("Default") || date_in_String.equals("default")) {
			long time_out = System.currentTimeMillis();
			date_in = new java.sql.Date(time_out);
		} else {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			java.util.Date date = format.parse(date_in_String);
			date_in = new java.sql.Date(date.getTime());
		}
		
		//validate Check In Date
		if(!validateCheckInDate(loan_id, date_in)){
			throw new CheckInDateNotValidException();
		} else {
			System.out.println("Ya its a valid check in date");
		}

		
		//set check-in date in book_loans table for corresponding entry
		String querySting_book_loans ="update book_loans set date_in='"+date_in+"' where loan_id="+loan_id+";";
		System.out.println(querySting_book_loans);
		DBConnection.udateSQLQuery(connection, querySting_book_loans);

		//update no_of_books_taken field of borrower table corresponding to the card_no
		String queryString_Borrower ="update borrower set no_of_books_taken = no_of_books_taken-1 where card_no = "+card_no+";";
		System.out.println(queryString_Borrower);
		DBConnection.udateSQLQuery(connection, queryString_Borrower);


		//update available_copies field of book_copies table corresponding to the book_id and branch_id
		String queryString_book_Copies ="update book_copies set available_copies = available_copies+1 where book_id = '"+book_id+"' and branch_id ="+branch_id+";";
		System.out.println(queryString_book_Copies);
		DBConnection.udateSQLQuery(connection, queryString_book_Copies);

		//calculate fine and update book_loans 
		float fine_amt = Fines.calculateFine(loan_id, date_in);
		String queryString_fines = null;
		if (fine_amt > 0.0f) {
			queryString_fines = "update fines set fine_amt="+fine_amt+" where loan_id="+loan_id+";";
		} else if (fine_amt==0){
			queryString_fines = "update fines set fine_amt="+fine_amt+", paid=true where loan_id="+loan_id+";";
		}
		System.out.println(queryString_fines);
		DBConnection.udateSQLQuery(connection, queryString_fines);
		
		DBConnection.closeConnection(connection);
		System.out.println("LOL we r done");

	}
	
	public static boolean validateCheckInDate(int loan_id, java.sql.Date date_in) throws QueryExecutionException, ConnectionErrorException, SQLException {
		boolean isValidCheckInDate = false;
		String querySting ="select loan_id from book_loans where date_out <='"+date_in+"' and loan_id="+loan_id+";";
		connection = DBConnection.getConnection();
		ResultSet resultSet = DBConnection.executeSQLQuery(connection, querySting);
		if(resultSet != null) {
			if(resultSet.next() == false){
				isValidCheckInDate = false;
			} else {
				isValidCheckInDate = true;
			}
		}
		return isValidCheckInDate;
	}

	public static String checkIn_Extnded(int loan_id, String date_in_String) throws ConnectionErrorException, QueryExecutionException, SQLException {
		String message = "";
		connection = DBConnection.getConnection();
		try {
			int card_no = 0;
			String book_id ="";
			int branch_id = 0;
			//Find Info from Loans Table
			String queryString = "select branch_id, book_id, card_no from book_loans where loan_id="+loan_id+";";
			ResultSet resultSet = DBConnection.executeSQLQuery(connection, queryString);
			if(resultSet != null) {
				//System.out.println("Got it");
				while(resultSet.next()){
					//System.out.println("Check In............");
					card_no = resultSet.getInt("card_no");
					book_id = resultSet.getString("book_id");
					branch_id = resultSet.getInt("branch_id");
					
					System.out.println(card_no + "++" + book_id + "**" + branch_id);
				}
			}
			resultSet.close();
			checkIn_Extended(loan_id, card_no, book_id, branch_id, date_in_String);
			message = "Check In Successful";
			DBConnection.closeConnection(connection);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			message = e.getMessage();
		}
		return message;
	}

}
