package com.exceptions;

public class BookCopiesNotAvailableException extends Exception{
	public BookCopiesNotAvailableException() {
		super("Exception::All the copies of this book has been borrowed.");
	}
	public String toString() {
		return ("BookCopiesNotAvailableException") ;
	}

}
