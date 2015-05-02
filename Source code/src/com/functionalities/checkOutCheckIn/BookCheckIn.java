package com.functionalities.checkOutCheckIn;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.connection.DBConnection;
import com.exceptions.BookIdNotValidException;
import com.exceptions.CardNotBelongsToNameException;
import com.exceptions.CardNumberNotValidException;
import com.exceptions.ConnectionErrorException;
import com.exceptions.NameNotExistException;
import com.exceptions.QueryExecutionException;


//don't call functions null as parameter pass ""
public class BookCheckIn {
	private static Connection connection;

	public static void validateBookId(String book_id) throws ConnectionErrorException, QueryExecutionException, SQLException, BookIdNotValidException {
		//validate book_id
		if(!CheckUtils.isBookIdValid(book_id)){
			throw new BookIdNotValidException();
		} else {
			System.out.println("Ya its a valid book Id");
		}
	}

	public static void validateCardNumber(int card_no) throws ConnectionErrorException, QueryExecutionException, SQLException, CardNumberNotValidException {
		//validate card_no
		if(!CheckUtils.isCardNoValid(card_no)){
			throw new CardNumberNotValidException();
		} else {
			System.out.println("Ya its a valid card");
		}
	}


	public static void validateBorrowerName(String name) throws ConnectionErrorException, QueryExecutionException, SQLException, NameNotExistException {
		//validate borrower name
		if(!CheckUtils.isValidName(name)) {
			throw new NameNotExistException();
		} else {
			System.out.println("Ya its a valid name");
		}
	}

	public static void validateCardNameCombination(int card_no, String name) throws ConnectionErrorException, QueryExecutionException, SQLException, CardNotBelongsToNameException {
		if(!CheckUtils.isValidNameForCard(card_no, name)) {
			throw new CardNotBelongsToNameException();
		} else {
			System.out.println("Ya this card belongs to the name specified.");
		}
	}


	public static ArrayList<BookLoanResults> getCheckedOutList(String book_id, int card_no, String name) throws ConnectionErrorException, QueryExecutionException, SQLException, BookIdNotValidException, CardNumberNotValidException, NameNotExistException, CardNotBelongsToNameException {

		connection = DBConnection.getConnection();

		//System.out.println("Here");
		ArrayList<BookLoanResults> checkedOutList = new ArrayList<BookLoanResults>();
		String queryString = null;

		//if any input is empty string set it null
		if (book_id.equals("")) {
			book_id = null;
		}
		if (card_no==0) {
			card_no = 0;
		}
		if (name.equals("")) {
			name = null;
		}

		//case 1
		if(book_id == null && card_no == 0 && name == null) {
			return null;
		}
		//case 2
		if(book_id != null && card_no == 0 && name == null) {
			validateBookId(book_id);
			queryString ="select loan_id, book_id, branch_id, card_no, date_out, due_date, date_in from book_loans where book_id='"+book_id+"' and date_in is null;";
		}
		//case 3
		if(book_id == null && card_no != 0 && name == null) {
			validateCardNumber(card_no);
			queryString ="select loan_id, book_id, branch_id, card_no, date_out, due_date, date_in from book_loans where card_no="+card_no+" and date_in is null;";
		}
		//case 4
		if(book_id == null && card_no == 0 && name != null) {
			validateBorrowerName(name);

			//find the card numbers for the name
			String cardNumbers = "";
			ArrayList<Integer> cardNumberList = CheckUtils.getCardNumberListFromName(name);
			for(int cardNo:cardNumberList) {
				String str = Integer.toString(cardNo);
				cardNumbers = cardNumbers +"," +str;	
			}
			cardNumbers = cardNumbers.substring(1);
			System.out.println(cardNumbers);

			queryString = "select loan_id, book_id, branch_id, card_no, date_out, due_date, date_in from book_loans where card_no in ("+cardNumbers+") and date_in is null;";

		}
		//case 5
		if(book_id != null && card_no != 0 && name == null) {
			validateBookId(book_id);
			validateCardNumber(card_no);
			queryString = "select loan_id, book_id, branch_id, card_no, date_out, due_date, date_in from book_loans where card_no="+card_no+" and book_id='"+book_id+"' and date_in is null;";
		}
		//case 6
		if(book_id == null && card_no != 0 && name != null) {
			validateCardNumber(card_no);
			validateBorrowerName(name);
			validateCardNameCombination(card_no, name);
			queryString ="select loan_id, book_id, branch_id, card_no, date_out, due_date, date_in from book_loans where card_no="+card_no+" and date_in is null;";			
		}
		//case 7
		if(book_id != null && card_no == 0 && name != null) {
			validateBookId(book_id);
			validateBorrowerName(name);

			//find the card numbers corresponding to  the name substring
			String cardNumbers = "";
			ArrayList<Integer> cardNumberList = CheckUtils.getCardNumberListFromName("name");
			for(int cardNo:cardNumberList) {
				String str = Integer.toString(cardNo);
				cardNumbers = cardNumbers +"," +str;	
			}
			cardNumbers = cardNumbers.substring(1);
			//System.out.println(cardNumbers);

			queryString = "select loan_id, book_id, branch_id, card_no, date_out, due_date, date_in from book_loans where book_id='"+book_id+"' and card_no in ("+cardNumbers+") and date_in is null;";

		}
		//case 8
		if(book_id != null && card_no != 0 && name != null) {
			validateBookId(book_id);
			validateCardNumber(card_no);
			validateBorrowerName(name);
			validateCardNameCombination(card_no, name);
			queryString = "select loan_id, book_id, branch_id, card_no, date_out, due_date, date_in from book_loans where card_no="+card_no+" and book_id='"+book_id+"' and date_in is null;";			
		}
		System.out.println(queryString);
		checkedOutList = getResultList(queryString);


		DBConnection.closeConnection(connection);
		return checkedOutList;
	}

	public static ArrayList<BookLoanResults> getResultList(String queryString) throws ConnectionErrorException, QueryExecutionException, SQLException {
		ArrayList<BookLoanResults> resultList = new ArrayList<BookLoanResults>();
		connection = DBConnection.getConnection();
		ResultSet resultSet = DBConnection.executeSQLQuery(connection, queryString);
		if(resultSet != null) {
			//System.out.println("Got it");
			while(resultSet.next()){
				//System.out.println("YA............");
				BookLoanResults tupule = new BookLoanResults();
				tupule.setLoan_id(resultSet.getInt("loan_id"));
				tupule.setBook_id(resultSet.getString("book_id"));
				tupule.setBranch_id(resultSet.getInt("branch_id"));
				tupule.setCard_no(resultSet.getInt("card_no"));
				tupule.setDate_out(resultSet.getDate("date_out"));
				tupule.setDue_date(resultSet.getDate("due_date"));
				//System.out.println(tupule.getLoan_id()+"######"+ resultSet.getDate("date_out")+"######"+tupule.getDate_out());
				resultList.add(tupule);
			}
		}
		resultSet.close();
		System.out.println("done");
		return resultList;
	}
	public static void checkIn(int loan_id, int card_no, String book_id, int branch_id) throws ConnectionErrorException, QueryExecutionException, SQLException {

		//crete the database connection
		connection = DBConnection.getConnection();

		//calculating check-in date
		long time_out = System.currentTimeMillis();
		java.sql.Date date_in = new java.sql.Date(time_out);

		//set check-in date in book_loans table for corresponding entry
		String querySting_book_loans ="update book_loans set date_in='"+date_in+"' where loan_id="+loan_id+";";
		DBConnection.udateSQLQuery(connection, querySting_book_loans);

		//update no_of_books_taken field of borrower table corresponding to the card_no
		String queryString_Borrower ="update borrower set no_of_books_taken = no_of_books_taken-1 where card_no = "+card_no+";";
		DBConnection.udateSQLQuery(connection, queryString_Borrower);


		//update available_copies field of book_copies table corresponding to the book_id and branch_id
		String queryString_book_Copies ="update book_copies set available_copies = available_copies+1 where book_id = '"+book_id+"' and branch_id ="+branch_id+";";
		DBConnection.udateSQLQuery(connection, queryString_book_Copies);

		//DBConnection.closeConnection(connection);
		System.out.println("LOL we r done");

	}
	public static String checkIn(int loan_id) throws ConnectionErrorException, QueryExecutionException, SQLException {
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
			checkIn(loan_id, card_no, book_id, branch_id);
			message = "Check In Successful";
			DBConnection.closeConnection(connection);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			message = e.getMessage();
		}
		return message;
	}

	public static Map<String,ArrayList> getCheckedOutListJsp(String book_id, int card_no, String name) {
		Map<String,ArrayList>  returnMap = new TreeMap<String, ArrayList>();
		ArrayList<BookLoanResults> resultList = null;
		String message ="";
		try {
			//System.out.println(book_id+"="+card_no+"="+name);
			resultList = getCheckedOutList(book_id, card_no, name);
			message = "";
			if(resultList != null) {
				if(resultList.size()==0) {
					message = "No Loaned items found.";
				}
				for (BookLoanResults result:resultList) {
					System.out.println(result.getLoan_id()+"######"+ result.getBook_id() +"######"+result.getBranch_id()+"######"+result.getCard_no()+"######"+result.getDate_out()+"######"+result.getDue_date());
					message = "_";
				}
			} else {
				message = "Some other Issue";
				//System.out.println("Some Issue");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			message = e.getMessage();
		}
		returnMap.put(message, resultList);
		return returnMap;
	}


	public static void main(String[] args) {
		String message ="";
		String book_id = "";//0192860925
		int card_no = 0;//9001-mark 9002-jon
		String name = "mark";//"mark";
		Map<String,ArrayList> map = getCheckedOutListJsp(book_id, card_no, name);

		Set<String> keySet = map.keySet();
		Iterator itKey = keySet.iterator();
	    while(itKey.hasNext()){
	    	//System.out.println(itKey.next()); //either this or next line
	    	message = (String) itKey.next();
	    }
	    Collection<ArrayList> values = map.values();
	    Iterator itVal = values.iterator();
	    ArrayList<BookLoanResults> loanList = null;
	    while(itVal.hasNext()){
	    	//System.out.println(itVal.next());
	    	loanList = (ArrayList)itVal.next();
	    }
		for (BookLoanResults result:loanList) {
			System.out.println(result.getLoan_id()+"######"+ result.getBook_id() +"######"+result.getBranch_id()+"######"+result.getCard_no()+"######"+result.getDate_out()+"######"+result.getDue_date());
		}


	}

	

}

/*
		String message ="";
		String book_id = "";//0192860925
		int card_no = 9001;//9001-mark 9002-jon
		String name = "";//"mark";
		Map<String,ArrayList> map = getCheckedOutListJsp(book_id, card_no, name);

		Set<String> keySet = map.keySet();
		Iterator itKey = keySet.iterator();
	    while(itKey.hasNext()){
	    	//System.out.println(itKey.next()); //either this or next line
	    	message = (String) itKey.next();
	    }
	    Collection<ArrayList> values = map.values();
	    Iterator itVal = values.iterator();
	    ArrayList<BookLoanResults> loanList = null;
	    while(itVal.hasNext()){
	    	//System.out.println(itVal.next());
	    	loanList = (ArrayList)itVal.next();
	    }
		for (BookLoanResults result:loanList) {
			System.out.println(result.getLoan_id()+"######"+ result.getBook_id() +"######"+result.getBranch_id()+"######"+result.getCard_no()+"######"+result.getDate_out()+"######"+result.getDue_date());
		}


 */
/*
 			String book_id = "0192860925";//0192860925
			int card_no = 9002;//9001-mark 9002-jon
			String name = "";//"mark";
			ArrayList<BookLoanResults> resultList = getCheckedOutList(book_id, card_no, name);
			if(resultList != null) {
				if(resultList.size()==0) {
					System.out.println("No Loan");
				}
				for (BookLoanResults result:resultList) {
					System.out.println(result.getLoan_id()+"######"+ result.getBook_id() +"######"+result.getBranch_id()+"######"+result.getCard_no()+"######"+result.getDate_out()+"######"+result.getDue_date());	
				}
			} else {
				System.out.println("Some Issue");
			}
			////////////////
			checkIn(1, 9001, "0192860925", 2);
			DBConnection.closeConnection(connection);

1,'0192860925',2,9001
 */