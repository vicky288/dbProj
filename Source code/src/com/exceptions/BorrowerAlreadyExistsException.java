package com.exceptions;

public class BorrowerAlreadyExistsException extends Exception{
	public BorrowerAlreadyExistsException() {
		super("Exception:: Borrower with the given details already has a library membership");
	}
	public String toString() {
		return("BorrowerAlreadyExistsException");
	}

}
