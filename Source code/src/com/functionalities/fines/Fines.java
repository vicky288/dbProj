package com.functionalities.fines;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.rmi.CORBA.Tie;

import com.connection.DBConnection;
import com.exceptions.ConnectionErrorException;
import com.exceptions.QueryExecutionException;

public class Fines {

	private static Connection connection = null;

	public static String payFine(int loan_id, float payment_amount) throws QueryExecutionException, ConnectionErrorException, SQLException {
		connection = DBConnection.getConnection();
		String message = "";
		//validate if the book is checked out
		message = isCheckedInBook(loan_id);
		if(!message.equals("")) {
			return message;
		}
		//update payment amount
		String queryString_finePayment = "update fines set fine_amt=fine_amt-"+payment_amount+" where loan_id="+loan_id+";";
		System.out.println(queryString_finePayment);
		DBConnection.udateSQLQuery(connection, queryString_finePayment);
		
		//update payment status
		String queryString = "select fine_amt from fines where loan_id="+loan_id+";";
		ResultSet resultSet = DBConnection.executeSQLQuery(connection, queryString);
		float fine_amt = 0.0f;
		if(resultSet != null) {
			//System.out.println("Got it");
			while(resultSet.next()){
				fine_amt = resultSet.getFloat("fine_amt");
			}
		}
		resultSet.close();
		if (fine_amt <= 0.0f) {
			String queryString_statusUpdate = "update fines set paid=true where loan_id="+loan_id+";";
			System.out.println(queryString_statusUpdate);
			DBConnection.udateSQLQuery(connection, queryString_statusUpdate);
		}
		
		System.out.println("fine paid");
		message = "Fine payment Successful for Loan Id: "+loan_id;
		return message;
	}
	public static float calculateFine(int loan_id, Date date_in) throws ConnectionErrorException, SQLException, QueryExecutionException {
		float fine_amt = 0.0f;
		
		//retrieve due date
		Date due_date = null;
		String queryString = "select due_date from book_loans where loan_id="+loan_id+";";
		connection = DBConnection.getConnection();
		ResultSet resultSet = DBConnection.executeSQLQuery(connection, queryString);
		if(resultSet != null) {
			//System.out.println("Got it");
			while(resultSet.next()){
				due_date = resultSet.getDate("due_date");
			}
		}
		//System.out.println("Due date"+due_date);
		//calculate fine
		long time_due = due_date.getTime();
		long time_in = date_in.getTime();
		long time_diff = time_in - time_due;
		int day_diff = (int) (time_diff / (24 * 60 * 60 * 1000));
		//System.out.println(day_diff);
		
		if (day_diff > 0) {
			fine_amt = day_diff * 0.25f;
			return fine_amt;
		} else {
			return 0;
		}
	}
	
	public static void calculateFineAll() throws SQLException, ConnectionErrorException, QueryExecutionException {
		// Find all Loan ids where where book is not returned
		ArrayList<Integer> loanIdList = getPendingLoanIds();
		
		//current date
		Date current_date = null;
		long current_time = System.currentTimeMillis();
		current_date = new java.sql.Date(current_time);

		
		//calculate fine for loanIds
		for(int loan_id:loanIdList) {
			float fine_amt = calculateFine(loan_id, current_date);
			refreshFine(loan_id, fine_amt);
		}
	}
	
	public static void refreshFine(int loan_id, float fine_amt) throws ConnectionErrorException, SQLException, QueryExecutionException {
		if (fine_amt > 0.0f) {
			connection = DBConnection.getConnection();
			String queryString_fines = "update fines set fine_amt="+fine_amt+" where loan_id="+loan_id+";";
			DBConnection.udateSQLQuery(connection, queryString_fines);
//			System.out.println("fine refresh");
		}
	}
	public static ArrayList<Integer> getPendingLoanIds() throws SQLException, ConnectionErrorException, QueryExecutionException{
		ArrayList<Integer> loanIds =  new ArrayList<Integer>();
		connection = DBConnection.getConnection();
		String queryString = "select fines.loan_id from fines,book_loans where paid is false and date_in is null and fines.loan_id=book_loans.loan_id;";
		ResultSet resultSet = DBConnection.executeSQLQuery(connection, queryString);
		if(resultSet != null) {
			//System.out.println("Got it");
			while(resultSet.next()){
				Integer loan_id = resultSet.getInt("loan_id");
				//System.out.println(id);
				loanIds.add(loan_id);
			}
		}
		resultSet.close();
		return loanIds;
	}
	
	public static ArrayList<FineResult> getUnPaidFineList() throws ConnectionErrorException, SQLException, QueryExecutionException {
		ArrayList<FineResult> fineList = new ArrayList<FineResult>();
		connection = DBConnection.getConnection();
		String queryString = "select loan_id,fine_amt from fines where paid is false and fine_amt > 0.0;";
		ResultSet resultSet = DBConnection.executeSQLQuery(connection, queryString);
		if(resultSet != null) {
			//System.out.println("Got it");
			while(resultSet.next()){
				FineResult fineEntry = new FineResult();
				Integer loan_id = resultSet.getInt("loan_id");
				float fine_amt = resultSet.getFloat("fine_amt");
				fineEntry.setLoan_id(loan_id);
				fineEntry.setFine_amount(fine_amt);
				//System.out.println(id);
				fineList.add(fineEntry);
			}
		}
		resultSet.close();
		return fineList;
	}
	
	public static String isCheckedInBook(int loan_id) throws QueryExecutionException, ConnectionErrorException, SQLException {
		String message= "";
		String queryString = "select loan_id from book_loans where date_in is not null and loan_id='"+loan_id+"';";
		connection = DBConnection.getConnection();
		ResultSet resultSet = DBConnection.executeSQLQuery(connection, queryString);
		if(resultSet != null) {
			if(resultSet.next() == false){
				message = "Can't Pay fines for Book Not Checked-in.";
			} else {
				message= "";
			}
		}
		resultSet.close();
		return message;
	}
	
	public static ArrayList<FineResultUser> getUnPaidFineListGrouped() throws ConnectionErrorException, SQLException, QueryExecutionException {
		ArrayList<FineResultUser> fineList = new ArrayList<FineResultUser>();
		connection = DBConnection.getConnection();
		String queryString = "select card_no,sum(fine_amt) as Total_Fine from fines,book_loans where fines.loan_id=book_loans.loan_id and paid is false and fine_amt > 0.0 group by card_no;";
		ResultSet resultSet = DBConnection.executeSQLQuery(connection, queryString);
		if(resultSet != null) {
			//System.out.println("Got it");
			while(resultSet.next()){
				FineResultUser fineEntry = new FineResultUser();
				Integer card_no = resultSet.getInt("card_no");
				float total_fine_amt = resultSet.getFloat("Total_Fine");
				fineEntry.setCard_no(card_no);
				fineEntry.setTotal_fine_amount(total_fine_amt);
				fineList.add(fineEntry);
			}
		}
		resultSet.close();
		return fineList;
	}
	public static void main(String[] args) throws ConnectionErrorException, SQLException, QueryExecutionException {
		
		ArrayList<FineResultUser> fineList = getUnPaidFineListGrouped();
		for(FineResultUser fineEntry:fineList ) {
			System.out.println(fineEntry.getCard_no()+"&&&&"+fineEntry.getTotal_fine_amount());
			
		}
		
		String payment_amount_String = "0.9f";
		payment_amount_String = payment_amount_String.trim();
		float payment_amount = 0.0f;
		if(payment_amount_String.equals("")) {
			payment_amount = 0.0f;
		} else {
			payment_amount = Float.valueOf(payment_amount_String);
		}
		System.out.println(payment_amount+1.0f);
//		long time_out = System.currentTimeMillis();
//		time_out = time_out + 15 * 24 * 60 * 60 * 1000;
//		Date date_in = new java.sql.Date(time_out);
//		System.out.println(date_in+"date in");
		//calculateFine(19, date_in);
/*		connection = DBConnection.getConnection();
		for(int i=1; i<=18;i++)
		{
			
			String queryString = "insert into fines(loan_id,fine_amt) values("+i+",0.0);";
			DBConnection.udateSQLQuery(connection, queryString);
			System.out.println(queryString);

		}
		DBConnection.closeConnection(connection);*/
	}
}
