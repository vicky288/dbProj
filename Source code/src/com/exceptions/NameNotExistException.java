package com.exceptions;

public class NameNotExistException extends Exception{
	public NameNotExistException(){
		super("Exception:: This name doesn't exist in Borrower Table.");
	}
	public String toString() {
		return("NameNotExistException");
	}

}
