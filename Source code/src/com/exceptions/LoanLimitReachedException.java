package com.exceptions;

public class LoanLimitReachedException extends Exception{
	public LoanLimitReachedException(){
		super("Exception::User has already taken 3 books. ");
	}
	public String toString(){
		return("LoanLimitReachedException");
	}
}
