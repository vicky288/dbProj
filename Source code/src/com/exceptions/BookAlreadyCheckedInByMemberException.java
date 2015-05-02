package com.exceptions;

public class BookAlreadyCheckedInByMemberException extends Exception{
	public BookAlreadyCheckedInByMemberException(){
		super("Exception:: Book has already been checked in by the member");
	}
	
	public String toString() {
		return("BookAlreadyCheckedInByMemberException");
	}
}
