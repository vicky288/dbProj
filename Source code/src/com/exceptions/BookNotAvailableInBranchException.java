package com.exceptions;

public class BookNotAvailableInBranchException extends Exception{
	public BookNotAvailableInBranchException () {
		super("Exception::This Book is not available in the branch specified.");
	}
	public String toString(){
		return ("BookNotAvailableInBranchException") ;
	}
}
