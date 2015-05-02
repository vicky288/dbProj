package com.exceptions;

public class CardNumberNotValidException extends Exception{
	public CardNumberNotValidException() {
		super("Exception::Card number not valid"); 
	}
	public String toString() {
		return("MemberNotValidException");
	}
}
