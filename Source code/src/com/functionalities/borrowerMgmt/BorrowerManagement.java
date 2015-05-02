package com.functionalities.borrowerMgmt;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.connection.DBConnection;
import com.exceptions.BorrowerAlreadyExistsException;
import com.exceptions.ConnectionErrorException;
import com.exceptions.QueryExecutionException;
//chomp all entry 
//all 6 vaues should come through input
public class BorrowerManagement {
	private static Connection connection;
	public static int createUser(Borrower borrower) throws ConnectionErrorException, QueryExecutionException, SQLException, BorrowerAlreadyExistsException {

		//Create connection to DB
		connection = DBConnection.getConnection();
		
		//Retrieve values from the Borrower object
		String fname = borrower.getFname();
		String lname = borrower.getLname();
		String address = borrower.getAddress();
		String city = borrower.getCity();
		String state = borrower.getState();
		String phone = borrower.getPhone();

		//check duplicate borrower
		String queryString ="select card_no from borrower where fname like '"+fname+"' and lname like '"+lname+"' and address like '"+address+"' and city like '"+city+"' and state like '"+state+"';";		
		ResultSet resultSet = DBConnection.executeSQLQuery(connection, queryString);
		if(resultSet != null) {
			if(resultSet.next() == false){
				System.out.println("Borrower not present");
			} else {
				throw new BorrowerAlreadyExistsException();
			}
		}
		
		//generate card_no
		int card_no = generateCardNoSequence();
		System.out.println(card_no);

		//insert row for new borrower
		String queryString_Borrower = "insert into borrower values("+card_no+", '"+fname+"', '"+lname+"', '"+address+"', '"+city+"', '"+state+"', '"+phone+"',0);";
		DBConnection.udateSQLQuery(connection, queryString_Borrower);
		
		DBConnection.closeConnection(connection);
		System.out.println("LOL we r done");
		return card_no;
	}
	public static int generateCardNoSequence() throws ConnectionErrorException, QueryExecutionException, SQLException {
		int newCardNo = 0;
		String queryString = "select max(card_no) as sequence from borrower;";
		connection = DBConnection.getConnection();
		ResultSet resultSet = DBConnection.executeSQLQuery(connection, queryString);
		if(resultSet != null) {
			while(resultSet.next()){
				newCardNo = resultSet.getInt("sequence");
				//System.out.println("loanId->"+newLoanId);
			}
		}
		resultSet.close();
		return newCardNo + 1;
	}
	
	
	public static String createUserJsp(Borrower borrower) {
		String message= "";
		try{
			int boorower_Id = createUser(borrower);
			message = "New Borrower Created. Borrower Id is : "+boorower_Id+" !!!";
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
			message = e.getMessage();
		}
		
		return message;
	}

	public static void main(String[] args) {

			Borrower borrower = new Borrower();
			borrower.setFname("vicky");
			borrower.setLname("pradhan");
			borrower.setAddress("123 peachtree");
			borrower.setCity("Richardson");
			borrower.setState("tx");
			borrower.setPhone("(214) 535-7531");

			String str= createUserJsp(borrower);
			System.out.println(str);

	}

}

/*
borrower.setFname("jared");
borrower.setLname("james");
borrower.setAddress("123 peachtree");
borrower.setCity("plano");
borrower.setState("tx");
*/
