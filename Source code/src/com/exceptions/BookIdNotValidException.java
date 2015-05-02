package com.exceptions;

public class BookIdNotValidException extends Exception{
	public BookIdNotValidException() {
		super("Exception:: Entered Book Id is not Valid.");
	}
	public String toString(){
		return("BookIdNotValidException");
	}
}
