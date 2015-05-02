package com.functionalities.checkOutCheckIn;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;




import com.connection.DBConnection;
import com.exceptions.BookCopiesNotAvailableException;
import com.exceptions.BookIdNotValidException;
import com.exceptions.BookNotAvailableInBranchException;
import com.exceptions.ConnectionErrorException;
import com.exceptions.InvalidBranchIdException;
import com.exceptions.LoanLimitReachedException;
import com.exceptions.CardNumberNotValidException;
import com.exceptions.QueryExecutionException;
//don't call functions null as parameter pass ""
public class BookCheckOut {
	private static Connection connection;
	public static int checkOut(String book_id, int branch_id, int card_no) throws ConnectionErrorException, QueryExecutionException, SQLException, BookNotAvailableInBranchException, BookCopiesNotAvailableException, InvalidBranchIdException, LoanLimitReachedException, CardNumberNotValidException, BookIdNotValidException {

		connection = DBConnection.getConnection();
		
		//Check for valid Book Id
		if(!CheckUtils.isBookIdValid(book_id)){
			throw new BookIdNotValidException();
		} else {
			System.out.println("Ya its a valid book Id");
		}

		//Check for valid Branch
		if(!isVaidBranch(branch_id)){
			throw new InvalidBranchIdException();
		} else {
			//System.out.println("Ya its a valid branch");
		}

		//Check for Book Availability
		int availableCopies = noOfBooksAvailable(book_id, branch_id);
		//System.out.println("availableCopies->"+availableCopies);

		//Check for Valid Card number
		if(!CheckUtils.isCardNoValid(card_no)){
			throw new CardNumberNotValidException();
		} else {
			//System.out.println("Ya its a valid member");
		}

		//Check for Loan Limit
		if(hasLoanLimitReached(card_no)){
			throw new LoanLimitReachedException();
		}else {
			//System.out.println("Ya Loan limit not reached");
		}



		//Calculating due date and checkout date
		long time_out = System.currentTimeMillis();
		java.sql.Date date_out = new java.sql.Date(time_out);
		System.out.println(date_out);
		long time_due = time_out + 14 * 24 * 60 * 60 * 1000;
		java.sql.Date due_date = new java.sql.Date(time_due);
		System.out.println(due_date);

		//find new loan_id Sequence
		int newLoan_id = CheckUtils.findLoanIdSequence();

		//Insert entry to Book_loans
		String queryString_BookLoans = "insert into book_loans values ("+newLoan_id+",'"+book_id+"',"+branch_id+","+card_no+",'"+date_out+"','"+due_date+"',"+null+");";
		connection = DBConnection.getConnection();
		DBConnection.udateSQLQuery(connection, queryString_BookLoans);

		//Update Borrower
		String queryString_Borrower ="update borrower set no_of_books_taken = no_of_books_taken+1 where card_no = "+card_no+";";
		DBConnection.udateSQLQuery(connection, queryString_Borrower);

		//Update book_copies 
		String queryString_bookCopies ="update book_copies set available_copies = available_copies-1 where book_id = '"+book_id+"' and branch_id ="+branch_id+";";
		DBConnection.udateSQLQuery(connection, queryString_bookCopies);

		//Insert Entry to fines 
		float loan_amt = 0.0f;
		String queryString_fines = "insert into fines(loan_id,fine_amt) values ("+newLoan_id+","+loan_amt+");";
		connection = DBConnection.getConnection();
		DBConnection.udateSQLQuery(connection, queryString_fines);
		
		DBConnection.closeConnection(connection);
		System.out.println("LOL we r done");
		return 288;
	}

	public static boolean isVaidBranch(int branch_id) throws ConnectionErrorException, QueryExecutionException, SQLException {
		boolean isValidBranch = false;
		String queryString = "select branch_id from library_branch where branch_id="+branch_id+";";
		connection = DBConnection.getConnection();
		ResultSet resultSet = DBConnection.executeSQLQuery(connection, queryString);
		if(resultSet != null) {
			if(resultSet.next() == false){
				isValidBranch = false;
			} else {
				isValidBranch = true;
			}
		}
		return isValidBranch;
	}
	public static int noOfBooksAvailable(String book_id, int branch_id) throws ConnectionErrorException, QueryExecutionException, SQLException, BookNotAvailableInBranchException, BookCopiesNotAvailableException {
		int availableCopies = 0;
		int no_of_copies = 0;
		String queryString = "select no_of_copies,available_copies from book_copies where book_id = '"+book_id+"' and branch_id = "+branch_id+";";
		connection = DBConnection.getConnection();
		ResultSet resultSet = DBConnection.executeSQLQuery(connection, queryString);
		ResultSet resultSet_test = DBConnection.executeSQLQuery(connection, queryString);
		if(resultSet != null) {
			if(resultSet_test.next() == false){
				throw new BookNotAvailableInBranchException();
			}
			while(resultSet.next()){
				no_of_copies = resultSet.getInt("no_of_copies");
				availableCopies = resultSet.getInt("available_copies");
			}
		}
		if(no_of_copies == 0) {
			throw new BookNotAvailableInBranchException();
		}
		if (availableCopies == 0) {
			throw new BookCopiesNotAvailableException();
		}
		return availableCopies;
	}

	public static boolean hasLoanLimitReached(int card_no) throws QueryExecutionException, ConnectionErrorException, SQLException {
		int noOfBooksTaken = 0;
		String queryString = "select no_of_books_taken from borrower where card_no = "+card_no+";";
		connection = DBConnection.getConnection();
		ResultSet resultSet = DBConnection.executeSQLQuery(connection, queryString);
		if(resultSet != null) {
			while(resultSet.next()){
				noOfBooksTaken = resultSet.getInt("no_of_books_taken");
				//System.out.println("noOfBooksTaken->"+noOfBooksTaken);
			}
		}
		if(noOfBooksTaken==3){
			return true;
		}
		return false;
	}
	public static void main(String[] args) {
		try {

			String book_id = "0192860925";
			int branch_id = 23;
			int card_no = 9001;
			String msg = checkOutJspString("600", "1", "90r01");
			System.out.println(msg+"$$$$");


		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static String checkOutJsp(String book_id, int branch_id, int card_no) {
		String message ="";
		try {
			checkOut(book_id, branch_id, card_no);
			message = "Book Checked out Successfully.";

		} catch (Exception e) {
			message = e.getMessage();
		}
		return message;
	}
	public static String checkOutJspString(String book_id, String branch_id_String, String card_no_String) {
		String message ="";
		try {
			
			int branch_id = Integer.valueOf(branch_id_String);
			int card_no = Integer.valueOf(card_no_String);
			checkOut(book_id, branch_id, card_no);
			message = "Book Checked out Successfully.";

		} catch (Exception e) {
			message = e.getMessage();
			if (message.indexOf("For input string:") >= 0) {
				message = "Book Id and Card number should be Number.";
			} 
		}
		return message;
	}
}
/*
String book_id = "0192860925"; 
int branch_id = 2;
int card_no = 9001;
 */